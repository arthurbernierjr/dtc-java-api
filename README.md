# Todos API (Java · Spring Boot)

Deeper Than Code backend example by Arthur Bernier Jr.: Java 25, Spring Boot 4.1.2, Spring Data JPA (Hibernate), Bean Validation, Postgres. Built with Maven and deployed on Railway from the `Dockerfile`.

Live: https://api-production-c419.up.railway.app

## The contract

| Request | Response |
|---|---|
| `GET /health` | `200 {"ok":true}` |
| `GET /todos` | `200 [{id,subject,details}]` ordered by id |
| `GET /todos/:id` | `200` object, or `404 {"error":"not found"}` |
| `POST /todos` body `{subject,details}` | `201` object |
| `PUT /todos/:id` body `{subject,details}` | `200` object, or `404 {"error":"not found"}` |
| `DELETE /todos/:id` | `204` empty, or `404 {"error":"not found"}` |
| blank or missing subject/details | `400 {"error":"subject and details are required"}` |
| malformed JSON | `400 {"error":"invalid json"}` |

Objects have exactly `id`, `subject`, `details`. CORS allows any origin for GET, POST, PUT, DELETE, OPTIONS with `Content-Type`.

## Check it

```sh
node check.mjs https://api-production-c419.up.railway.app
```

All 16 checks should print `PASS`.

## Environment

The app reads `PGHOST`, `PGPORT`, `PGDATABASE`, `PGUSER`, `PGPASSWORD` (Railway's Postgres reference variables) and `PORT` (falls back to 8080). Hibernate keeps the `todo` table in sync with `spring.jpa.hibernate.ddl-auto=update`.

Do not commit those values.

## Layout

- `src/main/java/learning/java/todos/entities/Todo.java` — the entity, `@NotBlank` on subject and details
- `src/main/java/learning/java/todos/repositories/TodoRepository.java` — Spring Data repository
- `src/main/java/learning/java/todos/controllers/TodoController.java` — the `/todos` routes
- `src/main/java/learning/java/todos/controllers/ApiErrors.java` — 400/404 JSON error bodies
- `src/main/java/learning/java/todos/controllers/HealthController.java` — `/health`
- `src/main/java/learning/java/todos/config/CorsConfig.java` — global CORS
- `src/main/resources/static/index.html` — the page at `/`
