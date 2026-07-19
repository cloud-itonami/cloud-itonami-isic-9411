(ns bizassoc.render-html
  "Build-time HTML renderer for `docs/samples/operator-console.html`.

  Closes flagship checklist item 2 (com-junkawasaki/root ADR-2607189300,
  Wave5 rollout ledger seq 22): this repo previously had NO demo page
  and no generator at all. This namespace drives the REAL actor stack
  (`bizassoc.operation` -> `bizassoc.governor` -> `bizassoc.store`)
  through a scenario adapted from this repo's own `bizassoc.sim` demo
  driver (`clojure -M:dev:run`, confirmed to run correctly against the
  real seeded position directory before this file was written -- this
  repo's own sim driver uses ids that DO match
  `bizassoc.store/demo-data` [position-1..4], so it was safe to reuse
  rather than author from scratch, unlike `cloud-itonami-isic-851`'s
  now-fixed `schoolops.sim` bug), rendered deterministically -- no
  invented numbers, no timestamps in the page content, byte-identical
  across reruns against the same seed (verified by diffing two
  consecutive runs).

  Usage: `clojure -M:dev:render-html [out-file]`
  (default `docs/samples/operator-console.html`)."
  (:require [clojure.string :as str]
            [bizassoc.store :as store]
            [bizassoc.operation :as op]
            [langgraph.graph :as g]))

(def ^:private operator
  {:actor-id "op-1" :actor-role :governing-body-officer :phase 3})

(defn- exec! [actor tid request]
  (g/run* actor {:request request :context operator} {:thread-id tid}))

(defn- approve! [actor tid]
  (g/run* actor {:approval {:status :approved :by "op-1"}}
          {:thread-id tid :resume? true}))

(defn run-demo!
  "Runs a fresh seeded store through a scenario mixing every disposition
  this actor can reach: position-1 (JPN, clean, no lobbying-
  registration regime, review current) clears a full lifecycle --
  `:member/intake` auto-commits clean (phase-3 no-capital-risk write),
  then `:position/verify` and `:lobbying/screen` each ALWAYS escalate
  at phase 3 (`:phase-approval`, governor otherwise clean) and are
  approved, then `:actuation/publish-position` ALWAYS escalates (the
  governor's own `high-stakes` gate -- publishing a real public
  advocacy position is never auto-eligible at any phase) and is
  approved, producing a real position-publication record
  (`JPN-POS-000000`). position-2 (ATL, an unregistered jurisdiction)
  HARD-holds `:position/verify` on `:no-spec-basis` -- never invent a
  jurisdiction's requirements. position-3 (JPN, review 120 days old
  against a 90-day max) clears its own `:position/verify` (approved),
  then HARD-holds `:actuation/publish-position` on
  `:position-review-overdue` -- the governor independently recomputes
  review recency, it does not trust the advisor. position-4 (USA,
  lobbying registration required but NOT confirmed) HARD-holds
  `:lobbying/screen` directly on `:lobbying-registration-unconfirmed`
  -- never reaches a human. Publishing position-1 a second time
  HARD-holds `:actuation/publish-position` on `:already-published` --
  the double-actuation guard. Every HARD hold never reaches a human.
  Returns the resulting store -- every field read by `render` below is
  real governor/store output, not a hand-typed copy."
  []
  (let [db (store/seed-db)
        actor (op/build db)]
    (exec! actor "p1-intake" {:op :member/intake :subject "position-1"
                               :patch {:id "position-1" :position-name "small-business-tax-relief-platform"}})

    (exec! actor "p1-verify" {:op :position/verify :subject "position-1"})
    (approve! actor "p1-verify")

    (exec! actor "p1-screen" {:op :lobbying/screen :subject "position-1"})
    (approve! actor "p1-screen")

    (exec! actor "p1-publish" {:op :actuation/publish-position :subject "position-1"})
    (approve! actor "p1-publish")

    (exec! actor "p2-verify" {:op :position/verify :subject "position-2" :no-spec? true})

    (exec! actor "p3-verify" {:op :position/verify :subject "position-3"})
    (approve! actor "p3-verify")

    (exec! actor "p3-publish" {:op :actuation/publish-position :subject "position-3"})

    (exec! actor "p4-screen" {:op :lobbying/screen :subject "position-4"})

    (exec! actor "p1-publish-again" {:op :actuation/publish-position :subject "position-1"})
    db))

;; ----------------------------- rendering -----------------------------

(defn- esc [v]
  (-> (str v)
      (str/replace "&" "&amp;")
      (str/replace "<" "&lt;")
      (str/replace ">" "&gt;")))

(defn- last-fact-for [ledger position-id]
  (last (filter #(= (:subject %) position-id) ledger)))

(defn- status-cell [ledger position-id]
  (let [f (last-fact-for ledger position-id)]
    (cond
      (nil? f) "<span class=\"muted\">no activity</span>"
      (= :committed (:t f)) "<span class=\"ok\">committed</span>"
      (= :approval-granted (:t f)) "<span class=\"ok\">approved &amp; committed</span>"
      (= :governor-hold (:t f))
      (let [rule (-> f :violations first :rule)]
        (str "<span class=\"critical\">HARD hold &middot; " (esc (name (or rule :unknown))) "</span>"))
      (= :approval-requested (:t f)) "<span class=\"warn\">awaiting approval</span>"
      :else "<span class=\"muted\">in progress</span>")))

(defn- lobbying-cell [{:keys [lobbying-registration-required? lobbying-registration-confirmed?]}]
  (cond
    (not lobbying-registration-required?) "<span class=\"muted\">not applicable</span>"
    lobbying-registration-confirmed? "<span class=\"ok\">confirmed</span>"
    :else "<span class=\"critical\">unconfirmed</span>"))

(defn- review-cell [{:keys [days-since-last-review max-review-interval-days]}]
  (let [overdue? (> days-since-last-review max-review-interval-days)]
    (str "<span class=\"" (if overdue? "critical" "ok") "\">"
         days-since-last-review "d / max " max-review-interval-days "d"
         (when overdue? " &middot; overdue") "</span>")))

(defn- position-row [ledger {:keys [id position-name jurisdiction published?] :as p}]
  (format "        <tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>"
          (esc id) (esc position-name) (esc jurisdiction)
          (lobbying-cell p) (review-cell p)
          (if published? "<span class=\"ok\">published</span>" "<span class=\"muted\">draft</span>")
          (status-cell ledger id)))

(defn- ledger-row [{:keys [t op subject disposition basis]}]
  (format "        <tr><td>%s</td><td><code>%s</code></td><td>%s</td><td>%s</td></tr>"
          (esc (name t)) (esc (name (or op :n-a))) (esc subject)
          (esc (or (some->> basis (map name) (str/join ", ")) (some-> disposition name) ""))))

(def ^:private action-gate-rows
  ;; Static description of this actor's own op contract (README `Ops`
  ;; table, `bizassoc.governor`/`bizassoc.phase`) -- documentation of
  ;; fixed behavior, not runtime telemetry, so it is legitimately
  ;; hand-described rather than derived from a live run.
  ["        <tr><td><code>:member/intake</code></td><td><span class=\"ok\">phase-3 auto-commit when clean, no capital/public risk</span></td></tr>"
   "        <tr><td><code>:position/verify</code></td><td><span class=\"warn\">ALWAYS human approval &middot; spec-basis independently checked</span></td></tr>"
   "        <tr><td><code>:lobbying/screen</code></td><td><span class=\"warn\">ALWAYS human approval &middot; lobbying-registration independently confirmed where required</span></td></tr>"
   "        <tr><td><code>:actuation/publish-position</code></td><td><span class=\"warn\">ALWAYS human approval &middot; never auto at any phase &middot; review recency + double-publication independently checked</span></td></tr>"])

(defn render
  "Renders the full operator-console.html document from a store `db`
  that has already run `run-demo!` (or any other real scenario)."
  [db]
  (let [ledger (vec (store/ledger db))
        positions (->> (store/all-positions db)
                       (filter #(#{"position-1" "position-2" "position-3" "position-4"} (:id %)))
                       (sort-by :id))
        position-rows (str/join "\n" (map (partial position-row ledger) positions))
        ledger-rows (str/join "\n" (map ledger-row ledger))]
    (str
     "<html><head><meta charset=\"utf-8\"><title>cloud-itonami-isic-9411 &middot; business and employers membership organizations</title><style>\n"
     "table { width: 100%; border-collapse: collapse; font-size: 14px; }\n"
     ".ok { color: #137a3f; }\n"
     "body { font-family: system-ui,-apple-system,sans-serif; margin: 0; color: #1a1a1a; background: #fafafa; }\n"
     "header.bar { display: flex; align-items: center; gap: 12px; padding: 12px 20px; background: #fff; border-bottom: 1px solid #e5e5e5; }\n"
     "th, td { text-align: left; padding: 8px 10px; border-bottom: 1px solid #f0f0f0; }\n"
     "h2 { margin-top: 0; font-size: 15px; }\n"
     ".warn { color: #b25c00; background: #fff8e1; padding: 2px 6px; border-radius: 4px; }\n"
     "main { max-width: 980px; margin: 24px auto; padding: 0 20px; }\n"
     "header.bar h1 { font-size: 18px; margin: 0; font-weight: 600; }\n"
     ".muted { color: #888; font-size: 13px; }\n"
     ".critical { color: #fff; background: #b3261e; padding: 2px 6px; border-radius: 4px; font-weight: 600; }\n"
     ".card { background: #fff; border: 1px solid #e5e5e5; border-radius: 8px; padding: 16px; margin-bottom: 16px; }\n"
     ".err { color: #b3261e; background: #fbe9e7; padding: 2px 6px; border-radius: 4px; }\n"
     "th { font-weight: 600; color: #555; font-size: 12px; text-transform: uppercase; letter-spacing: 0.04em; }\n"
     "header.bar .badge { margin-left: auto; font-size: 12px; color: #666; }\n"
     "code { font-size: 12px; background: #f4f4f4; padding: 1px 4px; border-radius: 3px; }\n"
     "</style></head><body>\n"
     "<header class=\"bar\">\n"
     "  <h1>Business and employers membership organizations (ISIC 9411) — Operator Console</h1>\n"
     "  <span class=\"badge\">read-only sample · governor-gated · position publication always human-approved</span>\n"
     "</header>\n"
     "<main>\n"
     "  <section class=\"card\">\n"
     "    <h2>Positions</h2>\n"
     "    <p class=\"muted\">Demo snapshot — build-time-generated from <code>bizassoc.store</code> via <code>bizassoc.render-html</code> (<code>clojure -M:dev:render-html</code>), regenerated nightly.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Position</th><th>Name</th><th>Jurisdiction</th><th>Lobbying registration</th><th>Review recency</th><th>Published</th><th>Last op status</th></tr></thead>\n"
     "      <tbody>\n"
     position-rows "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"
     "  <section class=\"card\">\n"
     "    <h2>Action gate (Association Governance Governor)</h2>\n"
     "    <p class=\"muted\">HARD holds cannot be overridden. A jurisdiction's requirements are never invented; lobbying-registration status and review recency are independently recomputed, never trusted from the proposal; a position can never be published twice.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Op</th><th>Gate</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" action-gate-rows) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"
     "  <section class=\"card\">\n"
     "    <h2>Audit ledger (this run)</h2>\n"
     "    <p class=\"muted\">Append-only decision-fact log — every proposal, hold and commit this scenario produced.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Fact</th><th>Op</th><th>Position</th><th>Basis</th></tr></thead>\n"
     "      <tbody>\n"
     ledger-rows "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"
     "</main>\n"
     "</body></html>\n")))

(defn -main [& args]
  (let [out (or (first args) "docs/samples/operator-console.html")
        db (run-demo!)
        html (render db)]
    (spit out html)
    (println "wrote" out "(" (count (store/ledger db)) "ledger facts,"
             (count (store/publication-history db)) "position-publications )")))
