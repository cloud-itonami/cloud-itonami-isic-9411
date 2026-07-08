# cloud-itonami-isic-9411

Open Business Blueprint for **ISIC Rev.5 9411**: Activities of
business and employers membership organizations.

This repository publishes a business/employer-membership-organization-
governance actor -- member enrollment intake, per-jurisdiction
association-governance regulatory assessment, lobbying-registration
screening and public-advocacy-position publication -- as an OSS
business that any qualified operator can fork, deploy, run, improve
and sell, so a community or independent provider never surrenders
member data and ledgers to a closed SaaS.

Built on this workspace's
[`langgraph`](https://github.com/kotoba-lang/langgraph)
StateGraph runtime (portable `.cljc`, supervised superstep loop,
interrupts, Datomic/in-mem checkpoints) -- the same actor pattern as
every prior actor in this fleet
([`cloud-itonami-isic-6511`](https://github.com/cloud-itonami/cloud-itonami-isic-6511),
[`6512`](https://github.com/cloud-itonami/cloud-itonami-isic-6512),
[`6621`](https://github.com/cloud-itonami/cloud-itonami-isic-6621),
[`6622`](https://github.com/cloud-itonami/cloud-itonami-isic-6622),
[`6629`](https://github.com/cloud-itonami/cloud-itonami-isic-6629),
[`6520`](https://github.com/cloud-itonami/cloud-itonami-isic-6520),
[`6530`](https://github.com/cloud-itonami/cloud-itonami-isic-6530),
[`6820`](https://github.com/cloud-itonami/cloud-itonami-isic-6820),
[`6612`](https://github.com/cloud-itonami/cloud-itonami-isic-6612),
[`6492`](https://github.com/cloud-itonami/cloud-itonami-isic-6492),
[`6920`](https://github.com/cloud-itonami/cloud-itonami-isic-6920),
[`6611`](https://github.com/cloud-itonami/cloud-itonami-isic-6611),
[`7120`](https://github.com/cloud-itonami/cloud-itonami-isic-7120),
[`8620`](https://github.com/cloud-itonami/cloud-itonami-isic-8620),
[`8530`](https://github.com/cloud-itonami/cloud-itonami-isic-8530),
[`9200`](https://github.com/cloud-itonami/cloud-itonami-isic-9200),
[`7500`](https://github.com/cloud-itonami/cloud-itonami-isic-7500),
[`9603`](https://github.com/cloud-itonami/cloud-itonami-isic-9603),
[`9521`](https://github.com/cloud-itonami/cloud-itonami-isic-9521),
[`9321`](https://github.com/cloud-itonami/cloud-itonami-isic-9321),
[`8730`](https://github.com/cloud-itonami/cloud-itonami-isic-8730),
[`9102`](https://github.com/cloud-itonami/cloud-itonami-isic-9102),
[`9103`](https://github.com/cloud-itonami/cloud-itonami-isic-9103),
[`9602`](https://github.com/cloud-itonami/cloud-itonami-isic-9602),
[`9000`](https://github.com/cloud-itonami/cloud-itonami-isic-9000),
[`8890`](https://github.com/cloud-itonami/cloud-itonami-isic-8890),
[`8610`](https://github.com/cloud-itonami/cloud-itonami-isic-8610),
[`9311`](https://github.com/cloud-itonami/cloud-itonami-isic-9311),
[`8510`](https://github.com/cloud-itonami/cloud-itonami-isic-8510),
[`9412`](https://github.com/cloud-itonami/cloud-itonami-isic-9412),
[`6491`](https://github.com/cloud-itonami/cloud-itonami-isic-6491),
[`8720`](https://github.com/cloud-itonami/cloud-itonami-isic-8720),
[`8521`](https://github.com/cloud-itonami/cloud-itonami-isic-8521),
[`6619`](https://github.com/cloud-itonami/cloud-itonami-isic-6619),
[`3600`](https://github.com/cloud-itonami/cloud-itonami-isic-3600),
[`6190`](https://github.com/cloud-itonami/cloud-itonami-isic-6190),
[`3030`](https://github.com/cloud-itonami/cloud-itonami-isic-3030),
[`3830`](https://github.com/cloud-itonami/cloud-itonami-isic-3830),
[`7020`](https://github.com/cloud-itonami/cloud-itonami-isic-7020),
[`9420`](https://github.com/cloud-itonami/cloud-itonami-isic-9420),
[`9491`](https://github.com/cloud-itonami/cloud-itonami-isic-9491),
[`2610`](https://github.com/cloud-itonami/cloud-itonami-isic-2610),
[`3512`](https://github.com/cloud-itonami/cloud-itonami-isic-3512),
[`8810`](https://github.com/cloud-itonami/cloud-itonami-isic-8810),
[`8691`](https://github.com/cloud-itonami/cloud-itonami-isic-8691),
[`8569`](https://github.com/cloud-itonami/cloud-itonami-isic-8569),
[`6419`](https://github.com/cloud-itonami/cloud-itonami-isic-6419),
[`7310`](https://github.com/cloud-itonami/cloud-itonami-isic-7310),
[`7320`](https://github.com/cloud-itonami/cloud-itonami-isic-7320),
[`7210`](https://github.com/cloud-itonami/cloud-itonami-isic-7210),
[`7410`](https://github.com/cloud-itonami/cloud-itonami-isic-7410),
[`8710`](https://github.com/cloud-itonami/cloud-itonami-isic-8710),
[`8541`](https://github.com/cloud-itonami/cloud-itonami-isic-8541),
[`8690`](https://github.com/cloud-itonami/cloud-itonami-isic-8690),
[`9601`](https://github.com/cloud-itonami/cloud-itonami-isic-9601),
[`6420`](https://github.com/cloud-itonami/cloud-itonami-isic-6420),
[`7420`](https://github.com/cloud-itonami/cloud-itonami-isic-7420),
[`9609`](https://github.com/cloud-itonami/cloud-itonami-isic-9609),
[`8550`](https://github.com/cloud-itonami/cloud-itonami-isic-8550),
[`7010`](https://github.com/cloud-itonami/cloud-itonami-isic-7010),
[`8790`](https://github.com/cloud-itonami/cloud-itonami-isic-8790),
[`8542`](https://github.com/cloud-itonami/cloud-itonami-isic-8542),
[`6411`](https://github.com/cloud-itonami/cloud-itonami-isic-6411),
[`7490`](https://github.com/cloud-itonami/cloud-itonami-isic-7490),
[`9319`](https://github.com/cloud-itonami/cloud-itonami-isic-9319),
[`9329`](https://github.com/cloud-itonami/cloud-itonami-isic-9329),
[`9312`](https://github.com/cloud-itonami/cloud-itonami-isic-9312),
[`9492`](https://github.com/cloud-itonami/cloud-itonami-isic-9492),
[`9499`](https://github.com/cloud-itonami/cloud-itonami-isic-9499),
[`9512`](https://github.com/cloud-itonami/cloud-itonami-isic-9512),
[`9522`](https://github.com/cloud-itonami/cloud-itonami-isic-9522),
[`7220`](https://github.com/cloud-itonami/cloud-itonami-isic-7220)) --
here it is **AssocOps-LLM ⊣ Association Governance Governor** -- the
SAME governor name `association`/9412 already uses, a deliberate,
honest reuse of the same association-governance-oversight business
archetype for a different high-stakes act (see `docs/adr/0001-
architecture.md` Decision 1 for why this is not a naming error, and
for why this is the FOURTH confirmation of the fleet-wide governor-
name-reuse precedent, and the SECOND on a family other than
`:repair-shop-governor`). This actor's own entity/op shape is instead
modeled closely on `memberorg`/9499's own architecture, not
`association`/9412's own certification/discipline shape -- the SAME
"shared governor name, different actuation shape, entity/op shape
borrowed from the sibling with the matching real shape" pattern
`memberorg`/9499 first established relative to `partyops`/9492.

> **Why an actor layer at all?** An LLM is great at drafting a member
> intake summary, normalizing records, and checking whether a
> position's own recorded last review actually stays within its own
> recorded maximum review interval -- but it has **no notion of which
> jurisdiction's association-governance/lobbying-registration law is
> official, no authority to publish a real public advocacy position,
> and no way to know on its own whether a mandatory lobbying-
> registration requirement has actually been satisfied**. Letting it
> publish a position directly invites fabricated regulatory citations,
> a stale advocacy position being republished unreviewed, and a
> genuine lobbying-registration compliance gap being quietly
> overlooked -- and liability, for whoever runs it. This project seals
> the AssocOps-LLM into a single node and wraps it with an independent
> **Association Governance Governor**, a human **approval workflow**,
> and an immutable **audit ledger**.

## Scope: what this actor does and does not do

This actor covers member enrollment intake through association-
governance regulatory assessment, lobbying-registration screening and
public-advocacy-position publication. It does **not**, by itself,
hold any registration required to operate a trade/business
association or employer federation in a given jurisdiction, and it
does not claim to. It also does not adjudicate whether a position
actually crosses a legal line itself -- `bizassoc.registry/position-
review-overdue?` is a pure ground-truth recompute against the
position's own recorded fields, not a legal determination. Whoever
deploys and operates a live instance (a qualified association
operator) supplies any jurisdiction-specific registration, the real
lobbying-disclosure-filing process and the real membership-management-
system integrations, and bears that jurisdiction's liability -- the
software supplies the governed, spec-cited, audited execution
scaffold so that operator does not have to build the compliance layer
from scratch.

### Actuation

**Publishing a real public advocacy position on the association's
behalf is never autonomous, at any phase, by construction.** Two
independent layers enforce this (`bizassoc.governor`'s `:actuation/
publish-position` high-stakes gate and `bizassoc.phase`'s phase table,
which never puts `:actuation/publish-position` in any phase's `:auto`
set) -- see `bizassoc.phase`'s docstring and
`test/bizassoc/phase_test.clj`'s
`publish-position-never-auto-at-any-phase`. The actor may draft, check
and recommend; a human governing-body officer is always the one who
actually publishes a position. Matching every prior single-actuation
sibling's shape, grounded directly in this blueprint's own README text
("No automated proposal, by itself, can complete the following
without governor approval and audit evidence: publishing a public
advocacy position or policy statement on the association's behalf")
-- a POSITIVE actuation (committing a real publication record),
structurally the closest sibling to `memberorg`/9499's and
`partyops`/9492's own `:actuation/publish-position` shape (same
"publish a position" act, genuinely different domain -- lobbying-
registration compliance for trade/business associations rather than
tax-exempt-status risk for civic/consumer/advocacy nonprofits or
campaign-finance disclaimers for political parties).

## The core contract

```
member intake + jurisdiction facts (bizassoc.facts, spec-cited)
        |
        v
   ┌───────────────────────┐   proposal      ┌───────────────────────┐
   │ AssocOps-LLM          │ ─────────────▶ │ Association                    │  (independent system)
   │ (sealed)              │  + citations    │ Governance Governor:         │
   └───────────────────────┘                 │ spec-basis · evidence-       │
          │                 commit ◀┼ incomplete · lobbying-           │
          │                         │ registration-unconfirmed              │
    record + ledger        escalate ┼ (conditional, NEW) · position-          │
          │              (ALWAYS for│ review-overdue (MAXIMUM-                 │
          │               :actuation│ ceiling, honest reuse) ·                  │
          │               /publish- │ already-published                          │
          ▼               position) └───────────────────────┘
      human approval
```

**The AssocOps-LLM never publishes a position the Association
Governance Governor would reject, and never does so without a human
sign-off.** Hard violations (fabricated regulatory requirements;
unsupported evidence; an unconfirmed lobbying registration where
required; an overdue position review; a double publication) force
**hold** and *cannot* be approved past; a clean publication proposal
still always routes to a human.

## Run

```bash
clojure -M:dev:run     # walk one clean single-actuation lifecycle + four HARD-hold cases through the actor
clojure -M:dev:test    # governor contract · phase invariants · store parity · registry conformance · facts coverage
clojure -M:lint        # clj-kondo (errors fail; CI mirrors this)
```

## Robotics premise

All cloud-itonami verticals are designed on the premise that a **robot
performs the physical domain work**. Here a document-courier robot
handles physical member-mailing fulfillment where used, under the
actor, gated by the independent **Association Governance Governor**.
The governor never dispatches hardware itself; `:high`/`:safety-
critical` actions require human sign-off.

## Open business

This repository is not only source code. It is a public, forkable
business model:

| Layer | What is open |
|---|---|
| OSS core | Actor runtime, Association Governance Governor, position-publication draft records, audit ledger |
| Business blueprint | Customer, offer, pricing, unit economics, sales motion |
| Operator playbook | How to fork, license, deploy and support the service in a jurisdiction |
| Trust controls | Governance, security reporting, actuation invariant, audit requirements |

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md) to start this as an
open business on itonami.cloud, and
[`docs/adr/0001-architecture.md`](docs/adr/0001-architecture.md) for the
full architecture and decision record.

## Capability layer

This blueprint resolves its technology stack via
[`kotoba-lang/industry`](https://github.com/kotoba-lang/industry) (ISIC
`9411`). This vertical's member/operational records are practice-
specific rather than a shared cross-operator data contract, so
`bizassoc.*` runs on the generic robotics/identity/forms/dmn/bpmn/
audit-ledger stack only -- no bespoke domain capability lib to
reference at all.

## Layout

| File | Role |
|---|---|
| `src/bizassoc/store.cljc` | **Store** protocol -- `MemStore` ‖ `DatomicStore` (`langchain.db`) + append-only audit ledger + position-publication history. No dynamically-filed sub-record -- the actuation op acts directly on a pre-seeded position, and the double-actuation guard checks a dedicated `:published?` boolean rather than a `:status` value |
| `src/bizassoc/registry.cljc` | Position-publication draft records, plus `position-review-overdue?` -- an HONEST, literal reuse of `memberorg.registry`'s own FOURTEENTH-instance MAXIMUM-ceiling check (the FIFTEENTH instance overall), not claimed as new |
| `src/bizassoc/facts.cljc` | Per-jurisdiction association-governance catalog AND a SEPARATE lobbying-registration/disclosure citation per jurisdiction (present for USA/GBR/DEU, honestly ABSENT for Japan, which has no direct equivalent regime) with an official spec-basis citation per entry, honest coverage reporting |
| `src/bizassoc/assocopsllm.cljc` | **AssocOps-LLM** -- `mock-advisor` ‖ `llm-advisor`; intake/jurisdiction-verification/lobbying-registration-screening/publication proposals |
| `src/bizassoc/governor.cljc` | **Association Governance Governor** -- 4 HARD checks (spec-basis · evidence-incomplete · lobbying-registration-unconfirmed, CONDITIONAL unconditional-evaluation, GENUINELY NEW, the 64th grounding of this discipline and the SECOND conditional variant · position-review-overdue, MAXIMUM-ceiling honest reuse, the 15th instance) + 1 guard (already-published) + 1 soft (confidence/actuation gate) |
| `src/bizassoc/phase.cljc` | **Phase 0→3** -- read-only → assisted intake → assisted verify → supervised (position publication always human; member intake is the ONLY auto-eligible op, no direct capital risk) |
| `src/bizassoc/operation.cljc` | **OperationActor** -- langgraph StateGraph |
| `src/bizassoc/sim.cljc` | demo driver |
| `test/bizassoc/*_test.clj` | governor contract · phase invariants · store parity · registry conformance · facts coverage |

## Business-process coverage (honest)

This actor covers member enrollment intake through association-
governance regulatory assessment, lobbying-registration screening and
public-advocacy-position publication -- the core governed lifecycle
this blueprint's own `docs/business-model.md` names as its Offer:

| Covered | Not covered (out of scope for this R0) |
|---|---|
| Member intake + per-jurisdiction evidence checklisting, HARD-gated on an official spec-basis citation (`:member/intake`/`:position/verify`) | Real membership-management-system integration, real lobbying-disclosure-filing itself (see `bizassoc.facts`'s docstring) |
| Lobbying-registration screening, CONDITIONAL on the position's own jurisdiction actually requiring registration, evaluated so the screening op itself can HARD-hold on its own finding (`:lobbying/screen`) | Member-dues/benefit administration -- deliberately outside this actor's R0 scope (see this blueprint's own Offer text) |
| Position publication, HARD-gated on full evidence, a confirmed lobbying registration (where required) and a current position review, plus a double-publication guard (`:actuation/publish-position`) | |
| Immutable audit ledger for every intake/verification/screening/publication decision | |

Extending coverage is additive: add the next gate (e.g. a member-dues-
compliance check) as its own governed op with its own HARD checks and
tests, following the SAME "an independent governor re-verifies against
the actor's own records before any real-world act" pattern this
repo's flagship op already establishes.

## Jurisdiction coverage (honest)

`bizassoc.facts/coverage` reports how many requested jurisdictions
actually have an official spec-basis in `bizassoc.facts/catalog` --
currently 4 seeded (JPN, USA, GBR, DEU) out of ~194 jurisdictions
worldwide. This is a starting catalog to prove the governor contract
end-to-end, not a claim of global coverage. Adding a jurisdiction is
additive: one map entry in `bizassoc.facts/catalog`, citing a real
official source -- never fabricate a jurisdiction's requirements to
make coverage look bigger. Note that the lobbying-registration sub-
citation is a SEPARATE, honest sub-coverage: only 3 of the 4 seeded
jurisdictions (USA, GBR, DEU) actually have a formal lobbyist/
lobbying-organization registration regime this catalog is aware of --
Japan does not, and this is recorded honestly (`bizassoc.facts/
lobbying-spec-basis` returns `nil` for `"JPN"`) rather than fabricated.

## Maturity

`:implemented` -- `AssocOps-LLM` + `Association Governance Governor`
run as real, tested code (see `Run` above), promoted from the
originally-published `:blueprint`-tier scaffold, modeled closely on
`memberorg`/9499's own architecture and the seventy-eight other prior
actors' architecture across this fleet. See
`docs/adr/0001-architecture.md` for the history and design.

## License

Code and implementation templates are AGPL-3.0-or-later.
