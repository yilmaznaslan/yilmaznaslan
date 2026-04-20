# What is spec-driven development?

**Spec-driven development** means building software from a clear **specification (spec)** first, then implementing code to match that spec.

A spec is a written source of truth that explains:
- what the system should do
- how APIs or events should look
- expected inputs and outputs
- business rules and edge cases

## In simple terms

Instead of saying, "let's just start coding and figure it out later," teams first agree on the rules.  
Then developers build, test, and review against those rules.

## How it usually works

1. Write or refine the spec (requirements, API contract, acceptance criteria).
2. Review it with product, engineering, and other stakeholders.
3. Implement code based on the approved spec.
4. Validate with tests and reviews that compare behavior to the spec.
5. Update the spec when requirements change.

## Common spec types

You already know one of the most popular ones: **OpenAPI** for REST endpoints.  
But there are many spec types teams use in spec-driven development:

- **REST API specs (OpenAPI/Swagger):** endpoints, methods, request/response models, status codes
- **Event/message specs (AsyncAPI, Avro, Protobuf, JSON Schema):** event names, payload format, versioning rules
- **Data model specs (ERD/schema docs, migration specs):** tables, fields, constraints, relationships
- **Behavior specs (user stories + acceptance criteria, Gherkin/BDD):** expected user/system behavior in scenarios
- **UI/Design specs (Figma flows, design tokens, component rules):** screen behavior, states, spacing, accessibility expectations
- **Integration specs (webhook contracts, third-party mapping docs):** how systems connect and what each side sends/receives
- **Infrastructure specs (Terraform, Kubernetes manifests, Helm values):** environment, networking, scaling, deployment rules
- **Non-functional specs (SLO/SLA, security requirements):** performance, reliability, observability, privacy, and security constraints

### Quick mapping example

- OpenAPI answers: "What does this REST endpoint look like?"
- AsyncAPI answers: "What does this event stream/message contract look like?"
- Gherkin answers: "What behavior should happen for this user scenario?"

## Why teams use it

- Fewer misunderstandings between business and engineering
- Better alignment across backend, frontend, QA, and integration teams
- Easier onboarding because decisions are documented
- Safer changes because there is a clear reference point

## Is there any danger?

Yes. Spec-driven development is useful, but it has common risks if done badly:

- **Specs become outdated:** code changes, but docs are not updated
- **Too much documentation overhead:** teams spend more time writing docs than shipping value
- **False sense of safety:** "we have a spec" does not automatically mean "we have quality"
- **Overly rigid process:** teams block progress for small changes instead of iterating
- **Spec without feedback:** if users are not involved, you can build the wrong thing very clearly

The key idea: the spec must stay **alive** and connected to real implementation and feedback.

## How it is done in reality

In real teams, it is usually lightweight and iterative:

1. Start with a small draft spec (not a huge document).
2. Review quickly with product + engineering + QA.
3. Build a first version (often behind a feature flag).
4. Validate with tests, demo feedback, and production signals.
5. Update spec and code together as learning changes requirements.

So in practice, teams do not "freeze everything first."  
They use specs as a shared contract, then improve it continuously.

## Who is involved

- **Product/Business:** defines expected outcomes and rules
- **Engineers:** design and implement based on the spec
- **QA/Test:** verifies behavior matches the spec
- **Architects/Tech leads:** keep consistency across systems

## A quick example

If a spec says `POST /orders` must return `201` with `orderId`, `status`, and `createdAt`, teams implement that contract exactly.  
Frontend and QA can work in parallel because they know the expected response format early.

## When spec-driven is not the best fit

You can still use specs, but keep them very light when:

- the problem is still unclear and you are exploring ideas
- you are building a short prototype or spike
- requirements change daily and speed matters more than formal contracts

In those cases, use a small one-page spec and iterate fast.

## A simple starter template

If your team is new to this, start each feature with:

1. **Goal:** what business/user problem are we solving?
2. **Contract:** API/event/input-output shape
3. **Rules:** validations, business logic, edge cases
4. **Non-functional:** performance/security/reliability needs
5. **Acceptance criteria:** how we know it is done
6. **Version/change notes:** what changed and why

This keeps spec work practical instead of heavy.

## Important note

Spec-driven development does **not** mean "write once and never change."  
It means: when things change, update the spec first (or together), then update the implementation.
