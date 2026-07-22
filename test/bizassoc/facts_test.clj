(ns bizassoc.facts-test
  (:require [clojure.test :refer [deftest is]]
            [bizassoc.facts :as facts]))

(deftest jpn-has-a-spec-basis
  (is (some? (facts/spec-basis "JPN")))
  (is (string? (:provenance (facts/spec-basis "JPN")))))

(deftest unknown-jurisdiction-has-no-fabricated-spec-basis
  (is (nil? (facts/spec-basis "ATL"))))

(deftest coverage-never-reports-a-missing-jurisdiction-as-covered
  (let [report (facts/coverage ["JPN" "ATL" "GBR"])]
    (is (= 2 (:covered report)))
    (is (= ["ATL"] (:missing-jurisdictions report)))
    (is (= ["GBR" "JPN"] (:covered-jurisdictions report)))))

(deftest required-evidence-satisfied-needs-every-item
  (let [all (facts/evidence-checklist "JPN")]
    (is (facts/required-evidence-satisfied? "JPN" all))
    (is (not (facts/required-evidence-satisfied? "JPN" (rest all))))
    (is (not (facts/required-evidence-satisfied? "ATL" all)) "no spec-basis -> never satisfied")))

(deftest jpn-honestly-has-no-lobbying-registration-regime
  (is (nil? (facts/lobbying-spec-basis "JPN"))
      "Japan has no formal lobbyist/lobbying-organization registration regime in this R0 catalog -- must not be fabricated"))

(deftest usa-gbr-deu-each-have-a-lobbying-spec-basis
  (doseq [iso3 ["USA" "GBR" "DEU"]]
    (is (some? (facts/lobbying-spec-basis iso3)) (str iso3 " lobbying-spec-basis"))
    (is (string? (:lobbying-provenance (facts/lobbying-spec-basis iso3))) (str iso3 " lobbying-provenance"))))

(deftest unknown-jurisdiction-has-no-lobbying-spec-basis
  (is (nil? (facts/lobbying-spec-basis "ATL"))))

(deftest nld-has-a-spec-basis
  (let [sb (facts/spec-basis "NLD")]
    (is (some? sb))
    (is (= "Netherlands" (:name sb)))
    (is (string? (:provenance sb)))
    (is (string? (:legal-basis sb)))
    (is (string? (:owner-authority sb)))
    (is (= 3 (count (facts/evidence-checklist "NLD")))
        "NLD has no lobbying-registration-review record -- same 3-item shape as JPN, not the 4-item USA/GBR/DEU shape")))

(deftest nld-required-evidence-satisfied-needs-every-item
  (let [all (facts/evidence-checklist "NLD")]
    (is (facts/required-evidence-satisfied? "NLD" all))
    (is (not (facts/required-evidence-satisfied? "NLD" (rest all))))))

(deftest nld-honestly-has-no-lobbying-registration-regime
  (is (nil? (facts/lobbying-spec-basis "NLD"))
      "The Netherlands has, as of the primary sources fetched for this catalog, only an ANNOUNCED (not-yet-enacted) intent to introduce a mandatory lobbyregister (Kamerstuk 28844-303, 8 May 2026) -- the pre-existing Tweede Kamer register since 2012 is a voluntary building-access-pass register, not a mandatory lobbying-activity-disclosure regime, so this must NOT be fabricated as equivalent to USA/GBR/DEU"))
