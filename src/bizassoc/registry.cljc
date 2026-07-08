(ns bizassoc.registry
  "Pure-function position-publication record construction -- an
  append-only association book-of-record draft, the business/employer-
  membership-organization analog of `cloud-itonami-isic-9499`'s
  `memberorg.registry`.

  Like every sibling actor's registry, there is no single international
  check-digit standard for a position-publication reference number --
  every association/jurisdiction assigns its own reference format.
  This namespace does NOT invent one; it builds a jurisdiction-scoped
  sequence number and validates the record's required fields, the
  same honest, non-fabricating discipline `bizassoc.facts` uses.

  `position-review-overdue?` is an HONEST, LITERAL reuse of
  `memberorg.registry`'s own FOURTEENTH-instance MAXIMUM-ceiling check
  (itself a reuse of `eldercare.registry/care-plan-review-overdue?`'s
  own periodic-review-overdue temporal shape) -- the FIFTEENTH
  instance overall, NOT claimed as new. Periodic board review of
  standing advocacy positions applies to trade/business associations
  just as much as civic/consumer/environmental membership
  organizations -- the same real-world governance practice, the same
  shape.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real membership-management system. It builds the RECORD
  an association would keep, not the act of publishing the position
  itself (that is `bizassoc.operation`'s `:actuation/publish-
  position`, always human-gated -- see README `Actuation`)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the association operator's own act, not this actor's. See README
  `Actuation`."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn position-review-overdue?
  "Does `position`'s own `:days-since-last-review` EXCEED its own
  `:max-review-interval-days`? An honest, literal reuse of
  `memberorg.registry/position-review-overdue?`'s own shape -- see ns
  docstring."
  [{:keys [days-since-last-review max-review-interval-days]}]
  (and (number? days-since-last-review) (number? max-review-interval-days)
       (> days-since-last-review max-review-interval-days)))

(defn register-position-publication
  "Validate + construct the POSITION-PUBLICATION registration DRAFT --
  the association's own act of publishing a real public advocacy
  position on its behalf. Pure function -- does not touch any real
  membership-management system; it builds the RECORD an association
  would keep. `bizassoc.governor` independently re-verifies the
  position's own lobbying-registration confirmation (when required)
  and review recency, and blocks a double-publication of the same
  position, before this is ever allowed to commit."
  [position-id jurisdiction sequence]
  (when-not (and position-id (not= position-id ""))
    (throw (ex-info "position-publication: position_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "position-publication: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "position-publication: sequence must be >= 0" {})))
  (let [publication-number (str (str/upper-case jurisdiction) "-POS-" (zero-pad sequence 6))
        record {"record_id" publication-number
                "kind" "position-publication-draft"
                "position_id" position-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "publication_number" publication-number
     "certificate" (unsigned-certificate "PositionPublication" publication-number publication-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
