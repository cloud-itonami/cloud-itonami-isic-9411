(ns bizassoc.governor
  "Association Governance Governor -- the independent compliance layer
  that earns the AssocOps-LLM the right to commit. The LLM has no
  notion of jurisdictional association-governance/lobbying-
  registration law, whether a position's own recorded last review
  actually stays within its own recorded maximum review interval, or
  when an act stops being a draft and becomes a real-world public
  position publication, so this MUST be a separate system able to
  *reject* a proposal and fall back to HOLD -- the business/employer-
  membership-organization analog of `cloud-itonami-isic-6512`'s
  `casualty.governor`.

  This is the FOURTH confirmation of the fleet-wide governor-name-
  reuse precedent `commrepair`/9512's own ADR-0001 established (1st:
  commrepair/9512 sharing `:repair-shop-governor` with repairshop/
  9521; 2nd: applianceshop/9522 sharing the SAME name a second time;
  3rd: socialresearch/7220 sharing `:research-integrity-governor` with
  research/7210, the first confirmation on a DIFFERENT governor-name
  family) -- and the SECOND confirmation on a governor-name family
  other than `:repair-shop-governor` (`:association-governance-
  governor`, shared with `association`/9412 rather than
  `:research-integrity-governor`). Sharing this governor name is
  honest: both actors perform association-governance oversight of a
  membership organization publishing/finalizing high-stakes acts on
  its members'/the public's behalf, differing only in WHAT that act is
  (9412: certifying/disciplining professional members; 9411:
  publishing a public advocacy position) -- see this repo's own
  `docs/adr/0001-architecture.md` Decision 1.

  This actor's actual entity/op shape (a `position` published via a
  single `:actuation/publish-position` act) is instead modeled closely
  on `memberorg`/9499's own architecture, NOT `association`/9412's own
  certification/discipline shape -- the SAME 'shared governor name,
  different actuation shape, entity/op shape borrowed from the
  sibling with the matching REAL shape' pattern `memberorg`/9499's own
  build first established relative to `partyops`/9492.

  Five checks, in priority order, ALL HARD violations: a human approver
  CANNOT override them (you don't get to approve your way past a
  fabricated jurisdiction spec-basis, incomplete evidence, an
  unconfirmed lobbying registration where required, an overdue
  position review, or a double publication of the same position). The
  confidence/actuation gate is SOFT: it asks a human to look (low
  confidence / actuation), and the human may approve -- but see
  `bizassoc.phase`: for `:stake :actuation/publish-position` (a real
  public act) NO phase ever allows auto-commit either. Two independent
  layers agree that actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source (`bizassoc.
                                       facts`), or invent one?
    2. Evidence incomplete         -- for `:actuation/publish-
                                       position`, has the jurisdiction
                                       actually been assessed with a
                                       full position-publication
                                       evidence checklist (member-
                                       consensus/governing-body-
                                       approval/publication-notice,
                                       plus lobbying-registration-
                                       review where applicable) on
                                       file?
    3. Lobbying registration
       unconfirmed                    -- for a position whose own
                                       record declares `:lobbying-
                                       registration-required? true`
                                       (i.e. the association's home
                                       jurisdiction actually has a
                                       formal lobbyist/lobbying-
                                       organization registration regime
                                       -- US/UK/Germany in this R0
                                       catalog, NOT Japan, which has no
                                       direct equivalent), INDEPENDENTLY
                                       check whether `:lobbying-
                                       registration-confirmed?` is true.
                                       A GENUINELY NEW concept (grep-
                                       verified absent fleet-wide --
                                       zero hits for 'lobbying-
                                       registration'/'lobbyist-
                                       registration'/'lobbyregister'/
                                       'consultant-lobbyist'/'lobbying
                                       disclosure act'), CONDITIONAL on
                                       the position's own `:lobbying-
                                       registration-required?` ground
                                       truth -- the SECOND conditional
                                       variant of the unconditional-
                                       evaluation-discipline family
                                       (`socialresearch.governor/
                                       human-subjects-review-
                                       unconfirmed-violations` was the
                                       first, at 63rd; this is the
                                       64th overall). Grounded in real
                                       lobbying-registration/
                                       disclosure law: US Lobbying
                                       Disclosure Act of 1995 (2 U.S.C.
                                       §1601 et seq.), UK Transparency
                                       of Lobbying, Non-Party
                                       Campaigning and Trade Union
                                       Administration Act 2014 Part 1
                                       (Register of Consultant
                                       Lobbyists), Germany's
                                       Lobbyregistergesetz (2021).
    4. Position review overdue     -- for `:actuation/publish-
                                       position`, INDEPENDENTLY
                                       recompute whether the
                                       position's own days-since-last-
                                       review exceeds its own max-
                                       review-interval-days
                                       (`bizassoc.registry/position-
                                       review-overdue?`) -- an HONEST,
                                       LITERAL reuse of `memberorg.
                                       registry`'s own FOURTEENTH-
                                       instance MAXIMUM-ceiling check
                                       (the FIFTEENTH instance
                                       overall), not claimed as new.
    5. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:actuation/
                                       publish-position` (a REAL public
                                       act) -> escalate.

  One more guard, double-publication prevention, is enforced but NOT
  listed as a numbered HARD check above because it needs no upstream
  comparison at all -- `already-published-violations` refuses to
  publish the SAME position twice, off a dedicated `:published?` fact
  (never a `:status` value) -- an honest, literal reuse of `memberorg.
  governor`'s own guard, informed by `cloud-itonami-isic-6492`'s
  status-lifecycle bug (ADR-2607071320)."
  (:require [bizassoc.facts :as facts]
            [bizassoc.registry :as registry]
            [bizassoc.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Publishing a real public advocacy position on the association's
  behalf is the ONE real-world actuation event this actor performs --
  a single-member set, matching `memberorg`'s (and every other single-
  actuation sibling's) shape, grounded directly in this blueprint's
  own README ('No automated proposal, by itself, can complete the
  following without governor approval and audit evidence: publishing
  a public advocacy position or policy statement on the association's
  behalf')."
  #{:actuation/publish-position})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:position/verify` (or `:actuation/publish-position`) proposal
  with no spec-basis citation is a HARD violation -- never invent a
  jurisdiction's association-governance requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:position/verify :actuation/publish-position} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:actuation/publish-position`, the jurisdiction's required
  member-consensus/governing-body-approval/publication-notice (plus
  lobbying-registration-review where applicable) evidence must
  actually be satisfied -- do not trust the advisor's self-reported
  confidence alone."
  [{:keys [op subject]} st]
  (when (= op :actuation/publish-position)
    (let [p (store/position st subject)
          verification (store/verify-of st subject)]
      (when-not (and verification
                     (facts/required-evidence-satisfied?
                      (:jurisdiction p) (:checklist verification)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(会員合意記録/運営機関承認記録/公表通知記録/該当する場合はロビー登録審査記録等)が充足していない状態での公表提案"}]))))

(defn- lobbying-registration-unconfirmed-violations
  "For a position whose own record declares `:lobbying-registration-
  required? true`, INDEPENDENTLY check whether `:lobbying-
  registration-confirmed?` is true -- a genuinely new concept (see ns
  docstring), CONDITIONAL on the position's own `:lobbying-
  registration-required?` ground truth (a position published from a
  jurisdiction with no formal lobbyist-registration regime, like Japan
  in this R0 catalog, has no such requirement at all). Scoped to
  `:lobbying/screen` and `:actuation/publish-position`, so the
  screening op itself can HARD-hold on its own finding, matching every
  prior unconditional-evaluation check's scoping shape."
  [{:keys [op subject]} st]
  (when (contains? #{:lobbying/screen :actuation/publish-position} op)
    (let [p (store/position st subject)]
      (when (and (true? (:lobbying-registration-required? p))
                 (not (true? (:lobbying-registration-confirmed? p))))
        [{:rule :lobbying-registration-unconfirmed
          :detail (str subject " は法域のロビー登録が必要だが未確認 -- 公表提案は進められない")}]))))

(defn- position-review-overdue-violations
  "For `:actuation/publish-position`, INDEPENDENTLY recompute whether
  the position's own days-since-last-review exceeds its own max-
  review-interval-days via `bizassoc.registry/position-review-
  overdue?` -- an honest, literal reuse of `memberorg.registry`'s own
  shape."
  [{:keys [op subject]} st]
  (when (= op :actuation/publish-position)
    (let [p (store/position st subject)]
      (when (registry/position-review-overdue? p)
        [{:rule :position-review-overdue
          :detail (str subject " の前回審査経過日数(" (:days-since-last-review p)
                      ")が最大審査間隔(" (:max-review-interval-days p) ")を超過している")}]))))

(defn- already-published-violations
  "For `:actuation/publish-position`, refuses to publish the SAME
  position twice, off a dedicated `:published?` fact -- an honest,
  literal reuse of `memberorg.governor`'s own guard."
  [{:keys [op subject]} st]
  (when (= op :actuation/publish-position)
    (when (store/position-already-published? st subject)
      [{:rule :already-published
        :detail (str subject " は既に公表済み")}])))

(defn check
  "Censors an AssocOps-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (lobbying-registration-unconfirmed-violations request st)
                           (position-review-overdue-violations request st)
                           (already-published-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
