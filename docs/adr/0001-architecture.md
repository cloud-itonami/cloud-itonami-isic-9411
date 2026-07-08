# ADR-0001: AssocOps-LLM ⊣ Association Governance Governor architecture

## Status

Accepted. `cloud-itonami-isic-9411` promoted from `:blueprint` to
`:implemented` in the `kotoba-lang/industry` registry.

## Context

`cloud-itonami-isic-9411` publishes an OSS business blueprint for
activities of business and employers membership organizations:
representing and advocating for member businesses/employers (chambers
of commerce, trade associations, employer federations). Like every
prior actor in this fleet, the blueprint alone is not an
implementation: this ADR records the governed-actor architecture that
promotes it to real, tested code, following the same langgraph
StateGraph + independent Governor + Phase 0→3 rollout pattern
established by `cloud-itonami-isic-6511` (life insurance) and applied
across seventy-eight prior siblings, most recently `cloud-itonami-
isic-7220` (research and experimental development on social sciences
and humanities).

This blueprint's own `:itonami.blueprint/governor` keyword,
`:association-governance-governor`, is IDENTICAL to `association`/
9412's (activities of professional membership organizations). Per the
fleet-wide governor-name-reuse precedent `commrepair`/9512's own
ADR-0001 established (confirmed a second time by `applianceshop`/9522
within the same cluster, and a third time by `socialresearch`/7220 on
a DIFFERENT governor-name family), sharing a governor name is
acceptable -- not a naming error -- when the underlying business
archetype is genuinely the same, provided the reuse is documented and
the new build brings its own genuinely differentiated, well-grounded
check. This build is the FOURTH confirmation overall, and the SECOND
on a governor-name family other than `:repair-shop-governor`.

## Decision

### Decision 1: governor-name reuse -- the fourth confirmation, second on a non-repair-shop family

`association`/9412 and `bizassoc`/9411 both perform association-
governance oversight of a membership organization taking a high-stakes
act on its members'/the public's behalf -- differing only in WHAT that
act is: 9412 certifies/disciplines professional members; 9411
publishes a public advocacy position. Reusing `:association-
governance-governor` is an honest reflection of that shared
archetype, not a naming error -- the same reasoning `commrepair`/9512's,
`applianceshop`/9522's and `socialresearch`/7220's own ADR-0001s
applied to their own governor-name families.

### Decision 2: entity/op shape borrowed from `memberorg`/9499, NOT from `association`/9412

Because 9411's real actuation (publishing a public advocacy position)
is structurally identical to `memberorg`/9499's own shape (not
`association`/9412's certification/discipline shape), this build's
entity (`position`) and op set (`:member/intake`, `:position/verify`,
`:lobbying/screen`, `:actuation/publish-position`) are modeled closely
on `memberorg`/9499's own architecture. This is the SAME "shared
governor name, different actuation shape, entity/op shape borrowed
from the sibling with the matching real shape" pattern `memberorg`/
9499 first established relative to `partyops`/9492.

### Decision 3: single-actuation shape

This blueprint's own README, business-model.md and operator-guide.md
consistently name only ONE real-world act: "publishing a public
advocacy position or policy statement on the association's behalf."
Matching `memberorg`/9499's (and every other single-actuation
sibling's) shape, `high-stakes` here is a one-member set,
`#{:actuation/publish-position}`.

### Decision 4: `position-review-overdue?` -- an honest, literal reuse

`bizassoc.registry/position-review-overdue?` is an HONEST, LITERAL
reuse of `memberorg.registry`'s own FOURTEENTH-instance MAXIMUM-
ceiling check (itself a reuse of `eldercare.registry/care-plan-
review-overdue?`'s own periodic-review-overdue temporal shape) -- the
FIFTEENTH instance overall, NOT claimed as new. Periodic board review
of standing advocacy positions applies to trade/business associations
just as much as civic/consumer/environmental membership organizations.

### Decision 5: `lobbying-registration-unconfirmed?` -- the 64th unconditional-evaluation grounding, a genuinely new concept, the SECOND conditional variant

Before writing this check, every prior sibling's governor/registry/
store namespaces across the entire fleet were grepped for `lobbying-
registration`, `lobbyist-registration`, `lobbyregister`, `consultant-
lobbyist` and `lobbying disclosure act` -- zero hits, confirming this
is a genuinely new concept. `lobbying-registration-unconfirmed-
violations` reuses the unconditional-evaluation-screening DISCIPLINE
(`casualty.governor/sanctions-violations`'s original fix) for the 64th
distinct application overall (most recently `socialresearch.governor/
human-subjects-review-unconfirmed-violations` at 63rd, the FIRST
conditional variant). This is the SECOND conditional variant: the
check only activates when a position's own record declares
`:lobbying-registration-required? true`. A position published from a
jurisdiction with no formal lobbyist/lobbying-organization
registration regime (Japan, honestly, in this R0 catalog) has no such
requirement at all -- forcing one onto every position regardless of
jurisdiction would itself be a fabricated requirement, the same
failure mode `bizassoc.facts` refuses to commit for an uncataloged
jurisdiction. Grounded in real lobbying-registration/disclosure law:
US Lobbying Disclosure Act of 1995 (2 U.S.C. §1601 et seq.), UK
Transparency of Lobbying, Non-Party Campaigning and Trade Union
Administration Act 2014 Part 1 (Register of Consultant Lobbyists),
Germany's Lobbyregistergesetz (2021). Gates `:lobbying/screen` and
`:actuation/publish-position`.

This check deliberately does NOT reuse `memberorg.governor/tax-
exempt-status-risk-unresolved-violations`, despite both concerning a
membership organization's public advocacy act: trade/business
associations are typically structured as business leagues (US IRC
§501(c)(6)) or member-governed corporate/association-law entities
elsewhere, which face NO equivalent charitable-status-jeopardizing-
from-lobbying concern -- lobbying on behalf of member businesses is
their express, permitted purpose. The load-bearing regulatory concern
for this domain is instead the PROCEDURAL lobbyist-registration duty,
a genuinely distinct legal regime.

### Decision 6: dedicated double-actuation-guard boolean

`:published?` is a dedicated boolean on the `position` record, never a
single `:status` value -- an honest, literal reuse of `memberorg.
governor`'s own guard, informed by `cloud-itonami-isic-6492`'s real
status-lifecycle bug (ADR-2607071320).

### Decision 7: Store protocol, MemStore + DatomicStore parity

`bizassoc.store/Store` is implemented by both `MemStore` (atom-backed,
default for dev/tests/demo) and `DatomicStore` (`langchain.db`-
backed), proven to satisfy the same contract in
`test/bizassoc/store_contract_test.clj` -- the same seam every sibling
actor uses so swapping the SSoT backend is a configuration change, not
a rewrite.

### Decision 8: Phase 0→3 rollout

Phase 3's `:auto` set has exactly one member, `:member/intake` (no
capital risk). `:position/verify` and `:lobbying/screen` are never
auto-eligible at any phase (matching every sibling's screening-op
posture), and `:actuation/publish-position` is permanently excluded
from every phase's `:auto` set -- a structural fact, not a rollout
milestone, enforced by BOTH `bizassoc.phase` and `bizassoc.governor`'s
`high-stakes` set independently.

### Decision 9: no bespoke domain capability lib, and no `blueprint.edn` field-sync fixes needed

This blueprint's own `:itonami.blueprint/required-technologies` names
no domain-specific capability beyond the generic robotics/identity/
forms/dmn/bpmn/audit-ledger stack -- there was no capability-lib
decision to make at all. This repo's `blueprint.edn` already had the
correct `isic-` prefixed `:id` and correctly populated `:required-
technologies`/`:optional-technologies` matching the `kotoba-lang/
industry` registry's own entry for `"9411"` exactly -- only the
`:maturity` field itself needed adding.

### Decision 10: mock + LLM advisor pair

`bizassoc.assocopsllm` provides `mock-advisor` (deterministic, default
everywhere -- the actor graph and governor contract run offline) and
`llm-advisor` (backed by `langchain.model/ChatModel`, with a defensive
EDN-proposal parser so a malformed LLM response degrades to a safe
low-confidence noop rather than ever auto-publishing a position).

## Alternatives considered

- **Reusing `memberorg.governor/tax-exempt-status-risk-unresolved-
  violations` directly.** Rejected: trade/business associations
  (typically 501(c)(6) business leagues) face no equivalent
  charitable-status-jeopardizing-from-lobbying concern -- their
  express purpose IS lobbying on behalf of member businesses. The
  genuinely load-bearing concern is the procedural lobbyist-
  registration duty, a distinct legal regime from 501(c)(3)/(c)(4)
  charitable-status restrictions.
- **An unconditional lobbying-registration check** (applying to every
  position regardless of jurisdiction). Rejected: not every
  jurisdiction has a formal lobbyist/lobbying-organization
  registration regime -- Japan, honestly, does not in this R0
  catalog. Forcing the check onto every position would fabricate a
  requirement, contradicting the honest-coverage discipline
  `bizassoc.facts` already commits to for jurisdictions.
- **Borrowing `association`/9412's own certification/discipline
  entity/op shape** instead of `memberorg`/9499's publish-position
  shape. Rejected: 9411's own blueprint text names publishing a public
  advocacy position as its ONE real-world act, not member
  certification or disciplinary referral -- the entity/op shape must
  match the blueprint's own actual text, not merely its shared
  governor name.

## Consequences

- Eightieth actor in this fleet (79 implemented before this build).
- Confirms the fleet-wide governor-name-reuse precedent a fourth
  time, and for the second time on a governor-name family other than
  `:repair-shop-governor` -- reinforcing it as a general pattern
  applicable across the whole fleet, not limited to any one cluster.
- Establishes a genuinely NEW unconditional-evaluation-screening
  concept (lobbying-registration-unconfirmed?, the SECOND conditional
  variant), grep-verified absent from every prior sibling before the
  claim was finalized.
- `MemStore` ‖ `DatomicStore` parity is proven by
  `test/bizassoc/store_contract_test.clj`, the same `:db-api`-driven
  swap pattern every sibling actor uses.
- 34 tests / 134 assertions pass; lint is clean; the demo
  (`clojure -M:dev:run`) walks one clean actuation lifecycle plus four
  HARD-hold scenarios end-to-end.
- `blueprint.edn` required no field-sync fixes this time (already
  correct) -- only the `:maturity` flip itself.
