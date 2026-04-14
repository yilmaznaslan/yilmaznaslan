# Standard Error Response Bodies Between Microservices (RFC 7807)

If each microservice returns errors in a different JSON format, consumers need custom parsing per dependency, retries become inconsistent, and incident debugging is slow.

A better approach is to standardize on **RFC 7807 Problem Details** (`application/problem+json`) and add a small set of safe extensions for distributed systems.

## Why RFC 7807

RFC 7807 defines a common JSON structure for HTTP API errors so clients can handle failures predictably across services.

Core fields:

- `type`: URI identifying the problem type
- `title`: short, human-readable summary
- `status`: HTTP status code
- `detail`: specific explanation for this occurrence
- `instance`: URI/reference for this specific error occurrence

This gives language-agnostic compatibility between teams and tools.

## Standard Response Contract

Set response header:

`Content-Type: application/problem+json`

Base body (RFC 7807):

```json
{
  "type": "https://docs.mycompany.com/problems/customer-not-found",
  "title": "Customer not found",
  "status": 404,
  "detail": "Customer with id '12345' was not found.",
  "instance": "/api/v1/customers/12345"
}
```

## Microservice Extensions (Recommended)

RFC 7807 allows extension members. Add a minimal set that helps machine decisions and observability:

```json
{
  "type": "https://docs.mycompany.com/problems/dependency-timeout",
  "title": "Dependency timeout",
  "status": 503,
  "detail": "Timed out while calling payment-service.",
  "instance": "/orders/checkout",
  "errorCode": "DEPENDENCY_TIMEOUT",
  "correlationId": "f0f6c5f9f8b94732a4ed2ce9f7776d6f",
  "service": "order-service",
  "retryable": true,
  "timestamp": "2026-04-14T10:20:00Z"
}
```

Recommended extension fields:

- `errorCode`: stable business/technical code for automation and alerting
- `correlationId`: end-to-end traceability across services
- `service`: producer service name
- `retryable`: hints client retry policy
- `timestamp`: UTC generation time

Keep extensions stable and documented.

## Status Code + Problem Type Strategy

Use HTTP status for category and `type`/`errorCode` for exact semantics.

Examples:

- `400` + `/problems/invalid-input` + `INVALID_INPUT`
- `404` + `/problems/customer-not-found` + `CUSTOMER_NOT_FOUND`
- `409` + `/problems/version-conflict` + `VERSION_CONFLICT`
- `422` + `/problems/business-rule-violation` + `BUSINESS_RULE_VIOLATION`
- `429` + `/problems/rate-limit-exceeded` + `RATE_LIMIT_EXCEEDED`
- `503` + `/problems/dependency-timeout` + `DEPENDENCY_TIMEOUT`

## Producer Rules (Service Returning the Error)

1. Always return `application/problem+json`
2. Always include RFC core fields
3. Include `correlationId` from incoming headers (or generate one)
4. Never leak secrets, stack traces, SQL, tokens, or internal hostnames
5. Keep `type` URIs stable and documented

## Consumer Rules (Service Calling Another Service)

1. Parse RFC fields first (`status`, `type`, `detail`)
2. Use `retryable` and `status` to drive retry/circuit breaker logic
3. Map known `type`/`errorCode` values to internal domain exceptions
4. Log `correlationId`, `type`, and `instance` for quick triage

## Validation Error Pattern

For input validation, include structured violations as an extension:

```json
{
  "type": "https://docs.mycompany.com/problems/invalid-input",
  "title": "Invalid input",
  "status": 400,
  "detail": "Request body contains invalid fields.",
  "instance": "/api/v1/customers",
  "errorCode": "INVALID_INPUT",
  "violations": [
    { "field": "email", "message": "must be a well-formed email address" },
    { "field": "age", "message": "must be greater than or equal to 18" }
  ]
}
```

## Versioning and Rollout

Adopt incrementally:

1. Publish a shared error contract doc with problem `type` catalog
2. Update one producer service to RFC 7807
3. Update consumers to read both legacy and RFC 7807 during migration
4. Enforce via API contract tests
5. Deprecate legacy format after all consumers migrate

## Quick Checklist

- [ ] `Content-Type` is `application/problem+json`
- [ ] RFC core fields are present
- [ ] `type` URIs are stable and documented
- [ ] Extensions are minimal and consistent
- [ ] Sensitive data is excluded
- [ ] `correlationId` is propagated and logged

## Final Takeaway

For microservices, standardizing error bodies with **RFC 7807** removes integration ambiguity, improves resilience behavior, and makes production incidents faster to investigate.

Use RFC core fields as the foundation, then add a small, documented extension set for distributed-system needs.
