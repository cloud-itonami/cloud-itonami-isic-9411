# Business Model: Activities of business and employers membership organizations

## Classification

- Repository: `cloud-itonami-isic-9411`
- ISIC Rev.5: `9411`
- Activity: activities of business and employers membership organizations -- representing and advocating for member businesses/employers
- Social impact: community access, data sovereignty, transparent audit

## Customer

- independent trade/business associations
- employer federations
- chamber-of-commerce-style bodies

## Offer

- member enrollment intake
- advocacy-position/policy proposal
- member-dues/benefit administration
- immutable audit ledger

## Revenue

- self-host setup: one-time implementation fee
- managed hosting: monthly subscription per association
- support: monthly retainer with SLA
- migration: import from an incumbent membership-management system
- per-member dues-processing fee

## Trust Controls

- no public advocacy position or policy statement is published on the association's behalf without human sign-off
- a fabricated member-consensus claim forces a hold, not an override
- a position from a jurisdiction that requires formal lobbyist/
  lobbying-organization registration cannot be published without a
  confirmed registration on file -- unconfirmed, this is a hold, never
  an override
- every publication path is auditable
- member data stays outside Git
- emergency manual override paths remain outside LLM control

## Association Governance Governor: decision rule

This vertical's governor shares its name (`:association-governance-
governor`) with `cloud-itonami-isic-9412`'s (activities of professional
membership organizations). This is a deliberate reuse, not a naming
error: both actors perform association-governance oversight of a
membership organization taking a high-stakes act on its members'/the
public's behalf, differing only in WHAT that act is (9412: certifying
or disciplining professional members; 9411: publishing a public
advocacy position). The genuinely distinguishing concern this vertical
adds is lobbying-registration compliance: trade/business associations
are typically structured as business leagues (US IRC §501(c)(6)) or
member-governed corporate/association-law entities elsewhere, which
face no equivalent charitable-status-jeopardizing-from-lobbying
concern `cloud-itonami-isic-9499` (other membership organizations)
already covers -- instead, the load-bearing regulatory concern is a
procedural one: formal lobbyist/lobbying-organization registration and
disclosure duties before publishing a position that constitutes a
lobbying communication. Not every jurisdiction has such a regime
(Japan, honestly, does not in this R0 catalog) -- a position published
from a jurisdiction with no such regime carries no registration
requirement at all.
