# REST API

## Domain API

- `GET /api/v1/workflows`: list records
- `GET /api/v1/workflows/{id}`: retrieve a record
- `POST /api/v1/workflows`: create a record
- `GET /api/v1/workflows/search`: repository-backed search

## Activity API

- `GET /api/v1/activity`: latest 50 events
- `GET /api/v1/activity/{resourceType}/{resourceId}`: history for a record

Creation and deletion actions are recorded in H2. This is a demonstration event history, **not** a tamper-proof security audit log. Authentication and authorization are not yet implemented.
