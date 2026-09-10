(ns bizassoc.facts
  "Per-jurisdiction business/employer-association-governance regulatory
  catalog -- the G2-style spec-basis table the Association Governance
  Governor checks every `:position/verify` proposal against ('did the
  advisor cite an OFFICIAL public source for this jurisdiction's
  association-governance requirements, or did it invent one?').

  This blueprint's own named example activities (chambers of commerce,
  trade associations, employer federations) are genuinely distinct
  from `memberorg`/9499's own domain (civic/social clubs, consumer
  organizations, environmental advocacy groups, most of which are
  structured as tax-exempt/charitable nonprofits where publishing a
  position risks jeopardizing the org's own charitable status). Trade/
  business associations are typically structured as business leagues
  (US IRC §501(c)(6)) or member-governed corporate/association-law
  entities elsewhere, which face NO equivalent charitable-status-
  jeopardizing-from-lobbying concern -- lobbying on behalf of member
  businesses is their express, permitted purpose. The real, load-
  bearing regulatory concern for THIS type of organization is instead
  a PROCEDURAL one: formal lobbyist/lobbying-organization registration
  and disclosure duties before publishing an advocacy position that
  constitutes a lobbying communication (US Lobbying Disclosure Act of
  1995, UK Transparency of Lobbying etc. Act 2014, Germany's
  Lobbyregistergesetz) -- NOT the substantive charitable-status-risk
  concern `memberorg.facts` already covers for civic/consumer/
  environmental membership organizations.

  Coverage is reported HONESTLY (see `coverage`), the same discipline
  every sibling actor's `facts` namespace uses: a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries. Japan is
  DELIBERATELY seeded WITHOUT a `:lobbying-*` sub-citation: unlike the
  US/UK/Germany, Japan has no direct equivalent formal lobbyist/
  lobbying-organization registration regime for trade/business
  associations -- inventing one to make coverage look bigger would be
  the exact fabrication this discipline forbids. See `bizassoc.
  governor/lobbying-registration-unconfirmed-violations` for how this
  is handled as a CONDITIONAL check rather than a universal one.

  The Netherlands (NLD) is ALSO seeded WITHOUT a `:lobbying-*`
  sub-citation, but for a materially different reason than Japan's
  (worth spelling out rather than collapsing the two into the same
  'no regime' bucket): the Tweede Kamer (House of Representatives) has
  run a public register of belangenbehartigers/lobbyisten since 1 July
  2012, but it is a VOLUNTARY register tied to a physical building
  access pass -- it does not cover organizations that lobby without
  requesting a pass, and it discloses no activity, contacts, or
  expenditure (confirmed directly from
  https://www.tweedekamer.nl/contact_en_bezoek/lobbyisten). Separately,
  as of the most recent primary source fetched for this catalog (a
  brief from the minister of Binnenlandse Zaken en Koninkrijksrelaties
  to the Tweede Kamer dated 8 May 2026, Kamerstuk 28844-303,
  'Transparantie van besluitvorming lobbyregister'), the cabinet has
  only ANNOUNCED an INTENT to introduce a mandatory lobbyregister --
  a legislative process ('wetgevingstraject') and stakeholder
  consultation are described as not yet complete, with a 'contouren-
  brief' expected only in the autumn of 2026 and a Tweede Kamer
  committee debate scheduled for 24 September 2026. There is therefore
  NO enacted legal-basis article to cite yet for a Dutch mandatory
  lobbying-registration regime akin to the US LDA / UK Registrar of
  Consultant Lobbyists / Germany's Lobbyregistergesetz -- citing one
  now would be fabricating a citation for a law that does not yet
  exist. This is a live gap, not a permanent one: once the announced
  wetsvoorstel is enacted, `bizassoc.facts/catalog`'s NLD entry should
  be revisited to add the (then real) `:lobbying-*` sub-citation.

  Canada (CAN) HAS a full `:lobbying-*` sub-citation, unlike Japan and
  the Netherlands: the federal Lobbying Act (R.S.C., 1985, c. 44 (4th
  Supp.)) requires 'the officer responsible for filing returns for a
  corporation or organization' to register with the Commissioner of
  Lobbying when an employee's communications with public office
  holders about legislation, regulations, policy, programs, or
  government funding 'constitute a significant part of the duties of
  one employee' (s. 7(1)) -- a real, currently-in-force registration
  duty a trade/business association's own in-house government-
  relations staff would trigger, distinct from Japan's 'no such regime
  concept exists' absence and the Netherlands' 'announced but not yet
  enacted' gap. Canada's governance basis is federal, not provincial:
  a trade/business association most commonly incorporates federally
  as a not-for-profit corporation under the Canada Not-for-profit
  Corporations Act (NFP Act, S.C. 2009, c. 23) via Corporations
  Canada, whose s. 124 duty-to-manage provision is the direct
  functional analogue of GBR's Companies Act director's-duties
  citation and DEU's BGB Vorstand citation.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  member-consensus-record/governing-body-approval-record/publication-
  notice-record evidence set (PLUS a lobbying-registration-review
  record where a jurisdiction actually has such a regime); `:legal-
  basis` / `:owner-authority` / `:provenance` are the G2 citation the
  governor requires before any `:position/verify` proposal can commit.
  `:lobbying-owner-authority` / `:lobbying-legal-basis` /
  `:lobbying-provenance` are the SEPARATE lobbying-registration/
  disclosure citation the governor's `lobbying-registration-
  unconfirmed?` check is grounded in -- present ONLY for jurisdictions
  that actually have such a regime."
  {"JPN" {:name "Japan"
          :owner-authority "法務省 (Ministry of Justice)"
          :legal-basis "一般社団法人及び一般財団法人に関する法律 -- 理事会の意思決定手続"
          :national-spec "一般社団法人の理事会承認を要する対外的意見表明の手続基準"
          :provenance "https://www.moj.go.jp/MINJI/minji06_00023.html"
          :required-evidence ["会員合意記録 (member-consensus record)"
                              "運営機関承認記録 (governing-body-approval record)"
                              "公表通知記録 (publication-notice record)"]}
   "USA" {:name "United States"
          :owner-authority "Internal Revenue Service (IRS)"
          :legal-basis "Internal Revenue Code §501(c)(6) (business-league tax-exemption requirements)"
          :national-spec "IRS business-league common-business-interest and non-inurement requirements"
          :provenance "https://www.irs.gov/charities-non-profits/other-non-profits/business-leagues"
          :required-evidence ["Member-consensus record"
                              "Governing-body-approval record"
                              "Lobbying-registration-review record"
                              "Publication-notice record"]
          :lobbying-owner-authority "Clerk of the U.S. House of Representatives / Secretary of the U.S. Senate"
          :lobbying-legal-basis "Lobbying Disclosure Act of 1995 (2 U.S.C. §1601 et seq.)"
          :lobbying-provenance "https://lobbyingdisclosure.house.gov/"}
   "GBR" {:name "United Kingdom"
          :owner-authority "Companies House"
          :legal-basis "Companies Act 2006 -- directors' duties and board-approval requirements"
          :national-spec "Companies Act 2006 board-approved-statement governance requirements"
          :provenance "https://www.gov.uk/government/organisations/companies-house"
          :required-evidence ["Member-consensus record"
                              "Governing-body-approval record"
                              "Lobbying-registration-review record"
                              "Publication-notice record"]
          :lobbying-owner-authority "Registrar of Consultant Lobbyists"
          :lobbying-legal-basis "Transparency of Lobbying, Non-Party Campaigning and Trade Union Administration Act 2014, Part 1"
          :lobbying-provenance "https://registrarofconsultantlobbyists.org.uk/"}
   "DEU" {:name "Germany"
          :owner-authority "Amtsgericht (Vereinsregister)"
          :legal-basis "Bürgerliches Gesetzbuch (BGB) §§21-79 -- Vereinsrecht (association-law board-decision requirements)"
          :national-spec "BGB Vereinsrecht Vorstandsbeschluss-Anforderungen"
          :provenance "https://www.gesetze-im-internet.de/bgb/"
          :required-evidence ["Mitgliederkonsensprotokoll (member-consensus record)"
                              "Vorstandsbeschluss (governing-body-approval record)"
                              "Lobbyregister-Prüfprotokoll (lobbying-registration-review record)"
                              "Veröffentlichungsmitteilung (publication-notice record)"]
          :lobbying-owner-authority "Deutscher Bundestag (Bundestagsverwaltung)"
          :lobbying-legal-basis "Gesetz über die Einrichtung und die Führung eines Lobbyregisters beim Deutschen Bundestag (Lobbyregistergesetz, 2021)"
          :lobbying-provenance "https://www.lobbyregister.bundestag.de/"}
   ;; NLD deliberately has NO `:lobbying-*` sub-citation -- see the
   ;; ns docstring's "The Netherlands (NLD) is ALSO seeded WITHOUT..."
   ;; paragraph for why this is a live, not-yet-enacted gap (an
   ;; announced wetsvoorstel, per Kamerstuk 28844-303 of 8 May 2026)
   ;; rather than Japan's "no such regime concept exists" absence.
   ;; The pre-existing Tweede Kamer belangenbehartigers/lobbyisten
   ;; register (since 1 July 2012) is a voluntary building-access-pass
   ;; register, not a mandatory lobbying-activity-disclosure regime --
   ;; it is deliberately NOT cited here as if it were the US/UK/DEU
   ;; equivalent.
   "NLD" {:name "Netherlands"
          :owner-authority "Kamer van Koophandel (KVK) -- Handelsregister"
          :legal-basis "Burgerlijk Wetboek (BW) Boek 2, Titel 2, Artikelen 26, 27, 29-30, 44-45 -- Verenigingsrecht (vereniging-oprichting via notariële akte/statuten, Handelsregister-inschrijvingsplicht, bestuur (governing-board) bestuurs- en vertegenwoordigingsbevoegdheid)"
          :national-spec "BW Boek 2 art. 44 lid 1 / art. 45 lid 1: behoudens statutaire beperkingen is het bestuur belast met het besturen en vertegenwoordigen van de vereniging -- het dichtstbijzijnde Nederlandse analoog van een board-approved-extern-optreden-vereiste"
          :provenance "https://wetten.overheid.nl/BWBR0003045/2024-01-01"
          :required-evidence ["Notulen algemene ledenvergadering (member-consensus record)"
                              "Bestuursbesluit (governing-body-approval record)"
                              "Bekendmakingsbericht (publication-notice record)"]}
   "CAN" {:name "Canada"
          :owner-authority "Corporations Canada, Innovation, Science and Economic Development Canada (ISED)"
          :legal-basis "Canada Not-for-profit Corporations Act (S.C. 2009, c. 23), s. 124 -- duty to manage or supervise management"
          :national-spec "NFP Act s. 124: 'Subject to this Act, the articles and any unanimous member agreement, the directors shall manage or supervise the management of the activities and affairs of a corporation.'"
          :provenance "https://laws-lois.justice.gc.ca/eng/acts/C-7.75/FullText.html"
          :required-evidence ["Member-consensus record"
                              "Governing-body-approval record"
                              "Lobbying-registration-review record"
                              "Publication-notice record"]
          :lobbying-owner-authority "Office of the Commissioner of Lobbying of Canada (OCL)"
          :lobbying-legal-basis "Lobbying Act (R.S.C., 1985, c. 44 (4th Supp.)), s. 7 -- in-house lobbyist registration for corporations and organizations"
          :lobbying-provenance "https://laws-lois.justice.gc.ca/eng/acts/l-12.4/FullText.html"}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to publish a
  position on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-isic-9411 R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog, not a survey of all ~194 "
                 "jurisdictions -- extend `bizassoc.facts/catalog`, "
                 "never fabricate a jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn lobbying-spec-basis
  "The jurisdiction's lobbying-registration/disclosure requirement map,
  or nil -- nil means this jurisdiction has NO formal lobbyist/
  lobbying-organization registration regime this catalog is aware of
  (honestly true for Japan and the Netherlands as of this R0 catalog,
  unlike the US/UK/Germany -- see `catalog`'s ns docstring for why
  Japan's and the Netherlands' absences are for different reasons)."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:lobbying-owner-authority sb)
      (select-keys sb [:lobbying-owner-authority :lobbying-legal-basis :lobbying-provenance]))))
