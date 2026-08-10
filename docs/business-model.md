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

| Package | Customer | Price shape |
|---|---|---|
| Self-host starter | association secretariat lead | setup fee + optional support |
| Managed Starter | one trade/business association or chamber-style body, 200–500 member companies, secretariat of a few staff | ¥30,000/月 flat |

**Market-anchored (2026-08-10)**: benchmarked against 7 real competitor
products at an assumed 200–500 member association. Association management
software is unusually open about price for B2B SaaS — **5 of the 7 publish
real numbers.** Published: **MemberClicks** — "MC Trade: Starting at $3,500
annually" (the trade-association/chamber product, ≈¥43,750/月 at ~¥150/$)
and "MC Professional: Starting at $4,500 annually" (≈¥56,250/月)
(<https://www.memberclicks.com/pricing>); **シクミネット** — ベーシック
月額利用料 33,600円〜(税込 36,960円〜) + 初期費用 180,000円〜(税込
198,000円〜) (<https://shikuminet.com/price/>); **Join It** — Starter
$29/月, Total $99/月 (≈¥14,850), Extra $199/月 (≈¥29,850)
(<https://www.joinit.com/pricing>); **WildApricot** — $66.00/mo at the
100-contact tier ($59.40 prepaid 1yr, $56.10 prepaid 2yr); the 250/500/2,000
tier prices sit behind a slider and were **not** readable, so the 200–500
member figure is unconfirmed (<https://www.wildapricot.com/pricing>);
**ClubExpress** — member-count-based hosting with a $20/month minimum for
general clubs and associations, and $0.10 per active member per month
(minimum $35/month) for leagues and state organizations; the full per-member
rate table is in a PDF that refused retrieval, so only the floor is
confirmed
(<https://help.clubexpress.com/hc/en-us/articles/40882955425179-Fees-Expenses>).
**Not published — this is an observation, not a gap**: **SMOOSY** (アトラス,
200+ Japanese academic/professional societies) quotes individually by member
count and feature set, 要問い合わせ
(<https://www.atlas.jp/services/membership>); **Glue Up** and **GrowthZone**
publish nothing on their own sites (a competitor's comparison page cites
"From $125/mo" and "~$3,985/yr" for them, but a rival's claim is not a
vendor's published price and is not used as an anchor here). The measured
core band is therefore ≈¥14,850–¥43,750/月. **¥30,000/月 sits at Join It's
top published tier and just under シクミネット's entry price**, which is the
honest position: this actor sells none of what fills out a full AMS — no
dues collection or payment processing, no event registration, no email
broadcast, no association website — so MemberClicks MC Trade (¥43,750) or
シクミネット (¥33,600 + ¥198,000 setup) money is not justifiable. What it
does carry, and what none of the 7 sell, is the compliance edge that
actually creates liability for a trade association: a position from a
jurisdiction requiring lobbyist registration cannot be published without a
confirmed registration on file, a fabricated member-consensus claim forces a
hold rather than an override, and publishing a position is never in any
phase's auto set — a human governing-body officer always signs. No setup fee
is charged on this tier; an association needing real dues processing keeps
its existing system alongside (this actor has no payment path at all).

**Subscribe (2026-08-10)**: a live Stripe Payment Link for the Managed
Starter tier (¥30,000/月 flat) is available now —
<https://buy.stripe.com/14AeVeaeHdNK2r6a66eEo08>. This is a no-code
Stripe-hosted checkout on Gftd Japan 株式会社's live account; nothing in
this repo's actor code changed. Fulfillment is manual today — after
subscribing, contact gftdcojp to arrange managed-tenant setup. **No
association has subscribed to this tier yet — this is a live, working
checkout with zero paid tenants, not a claim of existing revenue.**

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
