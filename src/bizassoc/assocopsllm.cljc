(ns bizassoc.assocopsllm
  "AssocOps-LLM client -- the *contained intelligence node* for the
  business/employer-membership-organization-governance actor (README:
  \"AssocOps-LLM\"), closely modeled on `cloud-itonami-isic-9499`'s
  `memberorg.memberorgopsllm`.

  It normalizes member intake, drafts a per-jurisdiction association-
  governance evidence checklist, screens positions for an unconfirmed
  lobbying registration (where required), and drafts the position-
  publication finalization. CRITICAL: it is a smart-but-untrusted
  advisor. It returns a *proposal* (with a rationale + the fields it
  cited), never a committed record or a real public publication.
  Every output is censored downstream by `bizassoc.governor` before
  anything touches the SSoT, and `:actuation/publish-position`
  proposals NEVER auto-commit at any phase -- see README `Actuation`.

  Like every sibling actor's advisor, this is a deterministic mock so
  the actor graph runs offline and the governor contract is exercised
  end-to-end. In production this calls a real LLM (kotoba-llm or
  equivalent) with the same proposal shape.

  Proposal shape (all kinds):
    {:summary    str            ; human-facing draft / finding
     :rationale  str            ; why -- SCANNED by the spec-basis gate
     :cites      [kw|str ..]    ; facts/sources the LLM used -- SCANNED too
     :effect     kw             ; how a commit would mutate the SSoT
     :stake      kw|nil         ; :actuation/publish-position | nil
     :confidence 0..1}"
  (:require #?(:clj  [clojure.edn :as edn]
               :cljs [cljs.reader :as edn])
            [clojure.string :as str]
            [bizassoc.facts :as facts]
            [bizassoc.registry :as registry]
            [bizassoc.store :as store]
            [langchain.model :as model]))

(defn- normalize-intake
  "Directory upsert -- the LLM only normalizes/validates the patch; it
  does not invent the position, lobbying-registration status or
  jurisdiction. High confidence, low stakes."
  [_db {:keys [patch]}]
  {:summary    (str "会員記録更新: " (pr-str (keys patch)))
   :rationale  "入力 patch の正規化のみ。新規事実の生成なし。"
   :cites      (vec (keys patch))
   :effect     :position/upsert
   :value      patch
   :stake      nil
   :confidence 0.97})

(defn- verify-jurisdiction
  "Per-jurisdiction association-governance evidence checklist draft.
  `:no-spec?` injects the failure mode we must defend against:
  proposing a checklist for a jurisdiction with NO official spec-basis
  in `bizassoc.facts` -- the Association Governance Governor must
  reject this (never invent a jurisdiction's requirements)."
  [db {:keys [subject no-spec?]}]
  (let [p (store/position db subject)
        iso3 (if no-spec? "ATL" (:jurisdiction p))
        sb (facts/spec-basis iso3)]
    (if (nil? sb)
      {:summary    (str iso3 " の公式spec-basisが見つかりません")
       :rationale  "bizassoc.facts に未登録の法域。要件を推測で作らない。"
       :cites      []
       :effect     :verification/set
       :value      {:jurisdiction iso3 :checklist [] :spec-basis nil}
       :stake      nil
       :confidence 0.9}
      {:summary    (str iso3 " (" (:owner-authority sb) ") 向け必要書類 "
                        (count (:required-evidence sb)) " 件を提案")
       :rationale  (str "公式ソース: " (:provenance sb) " / 法的根拠: " (:legal-basis sb))
       :cites      [(:legal-basis sb) (:provenance sb)]
       :effect     :verification/set
       :value      {:jurisdiction iso3
                    :checklist (:required-evidence sb)
                    :spec-basis (:provenance sb)
                    :legal-basis (:legal-basis sb)}
       :stake      nil
       :confidence 0.9})))

(defn- screen-lobbying-registration
  "Lobbying-registration screening draft -- the genuinely new
  screening concern this vertical adds. `:lobbying-registration-
  confirmed? false` on a position that itself requires lobbying
  registration injects the failure mode: the Association Governance
  Governor must HOLD, un-overridably, on any unconfirmed lobbying
  registration."
  [db {:keys [subject]}]
  (let [p (store/position db subject)]
    (cond
      (nil? p)
      {:summary "対象の公表案件が見つかりません" :rationale "no position record"
       :cites [] :effect :lobbying-screen/set :value {:position-id subject :verdict :unknown}
       :stake nil :confidence 0.0}

      (not (true? (:lobbying-registration-required? p)))
      {:summary    (str (:position-name p) ": この法域にロビー登録制度なし -- 審査不要")
       :rationale  "lobbying-registration-required? が false のため、ロビー登録要件そのものが発生しない。"
       :cites      [:lobbying-registration-determination]
       :effect     :lobbying-screen/set
       :value      {:position-id subject :verdict :not-applicable}
       :stake      nil
       :confidence 0.9}

      (not (true? (:lobbying-registration-confirmed? p)))
      {:summary    (str (:position-name p) ": ロビー登録が未確認")
       :rationale  "ロビー登録が必要な法域だが登録状況が未確認。人手確認とホールドが必須。"
       :cites      [:lobbying-registration-check]
       :effect     :lobbying-screen/set
       :value      {:position-id subject :verdict :unconfirmed}
       :stake      nil
       :confidence 0.95}

      :else
      {:summary    (str (:position-name p) ": ロビー登録確認済み")
       :rationale  "ロビー登録が必要な法域、登録状況確認済み。"
       :cites      [:lobbying-registration-check]
       :effect     :lobbying-screen/set
       :value      {:position-id subject :verdict :confirmed}
       :stake      nil
       :confidence 0.9})))

(defn- propose-position-publication
  "Draft the actual POSITION-PUBLICATION action -- publishing a real
  public advocacy position on the association's behalf. ALWAYS `:stake
  :actuation/publish-position` -- this is a REAL-WORLD, reputation-
  and lobbying-compliance-affecting act, never a draft the actor may
  auto-run. See README `Actuation`: no phase ever adds this op to a
  phase's `:auto` set (`bizassoc.phase`); the governor also always
  escalates on `:actuation/publish-position`. Two independent layers
  agree, deliberately."
  [db {:keys [subject]}]
  (let [p (store/position db subject)
        ready? (and p (or (not (:lobbying-registration-required? p))
                          (:lobbying-registration-confirmed? p))
                   (not (registry/position-review-overdue? p)))]
    {:summary    (str subject " 向け公表提案"
                      (when p (str " (position=" (:position-name p) ")")))
     :rationale  (if p
                   (str "lobbying-registration-required?=" (:lobbying-registration-required? p)
                        " lobbying-registration-confirmed?=" (:lobbying-registration-confirmed? p)
                        " days-since-last-review=" (:days-since-last-review p)
                        " max-review-interval-days=" (:max-review-interval-days p))
                   "公表案件が見つかりません")
     :cites      (if p [subject] [])
     :effect     :position/mark-published
     :value      {:position-id subject}
     :stake      :actuation/publish-position
     :confidence (if ready? 0.9 0.3)}))

(defn infer
  "Route a request to the right proposal generator.
  request: {:op kw :subject id ...op-specific...}"
  [db {:keys [op] :as request}]
  (case op
    :member/intake                (normalize-intake db request)
    :position/verify               (verify-jurisdiction db request)
    :lobbying/screen                (screen-lobbying-registration db request)
    :actuation/publish-position       (propose-position-publication db request)
    {:summary "未対応の操作" :rationale (str op) :cites []
     :effect :noop :stake nil :confidence 0.0}))

;; ----------------------------- Advisor protocol -----------------------------

(defprotocol Advisor
  (-advise [advisor store request] "store + request -> proposal map"))

(defn mock-advisor
  "The deterministic advisor (the `infer` logic above). Default everywhere."
  [] (reify Advisor (-advise [_ st req] (infer st req))))

(def ^:private system-prompt
  (str "あなたは業界団体・使用者団体の公表エージェントの助言者です。"
       "与えられた事実のみに基づき、提案を1つだけEDNマップで返します。説明や前置きは"
       "一切書かず、EDNだけを出力します。\n"
       "キー: :summary(人向けドラフト) :rationale(根拠/必ず事実から) "
       ":cites(使った事実キーのベクタ) "
       ":effect(:position/upsert|:verification/set|:lobbying-screen/set|"
       ":position/mark-published) "
       ":stake(:actuation/publish-position か nil) :confidence(0..1)。\n"
       "重要: 登録されていない法域の要件を絶対に創作してはいけません。"
       "spec-basisが無い場合は :cites を空にし confidence を上げないこと。"
       "ロビー登録状況を偽って報告してはいけません。"))

(defn- facts-for [st {:keys [op subject]}]
  (case op
    :position/verify               {:position (store/position st subject)}
    :lobbying/screen                {:position (store/position st subject)}
    :actuation/publish-position       {:position (store/position st subject)}
    {:position (store/position st subject)}))

(defn- parse-proposal
  "Parse the model's EDN proposal defensively. Any parse/shape failure
  yields a safe low-confidence noop so the Association Governance
  Governor escalates/holds -- an LLM hiccup can never auto-publish a
  position."
  [content]
  (let [p (try (edn/read-string (str/trim (str content)))
               (catch #?(:clj Exception :cljs :default) _ nil))]
    (if (map? p)
      (-> p
          (update :cites #(vec (or % [])))
          (update :confidence #(if (number? %) (double %) 0.0))
          (update :effect #(or % :noop)))
      {:summary "LLM応答を解釈できませんでした" :rationale (str content)
       :cites [] :effect :noop :stake nil :confidence 0.0})))

(defn llm-advisor
  "An advisor backed by a `langchain.model/ChatModel` (real inference)."
  ([chat-model] (llm-advisor chat-model {}))
  ([chat-model gen-opts]
   (reify Advisor
     (-advise [_ st req]
       (let [msgs [{:role :system :content system-prompt}
                   {:role :user :content (str "操作: " (:op req)
                                              "\n対象: " (:subject req)
                                              "\n事実: " (pr-str (facts-for st req)))}]
             resp (model/-generate chat-model msgs gen-opts)]
         (parse-proposal (:content resp)))))))

(defn trace
  "Decision-grounded audit record -- persisted to the :audit channel."
  [request proposal]
  {:t          :assocopsllm-proposal
   :op         (:op request)
   :subject    (:subject request)
   :summary    (:summary proposal)
   :rationale  (:rationale proposal)
   :cites      (:cites proposal)
   :confidence (:confidence proposal)})
