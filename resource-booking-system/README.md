# Resource Booking System — Spring Boot + MySQL + STS

A secure RESTful resource booking API built with Java 17+, Spring Boot, Spring Security, JWT, JPA/Hibernate, and MySQL. It is ready to import as an **Existing Maven Project** in Spring Tool Suite (STS).

## Requirements

- Java 17 or later
- Spring Tool Suite (STS) 4.x
- MySQL 8.x (or compatible MySQL server)
- Maven 3.9+ if running from a terminal (STS can use the Maven wrapper if one is added)

## 1. Create the MySQL database

Open MySQL Workbench or the MySQL command line and run:

```sql
CREATE DATABASE resource_booking_db;
```

The application uses `spring.jpa.hibernate.ddl-auto=update`, so the tables are created/updated automatically when the application starts.

## 2. Configure MySQL

Edit `src/main/resources/application.yml` if necessary:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/resource_booking_db?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true
    username: root
    password: YOUR_MYSQL_PASSWORD
    driver-class-name: com.mysql.cj.jdbc.Driver
```

For better security, use environment variables instead of committing a password:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`
- `SERVER_PORT`

The defaults are suitable for local development except the MySQL password, which is intentionally blank.

## 3. Import into STS

1. Extract the ZIP file.
2. Open Spring Tool Suite.
3. Select **File → Import**.
4. Select **Maven → Existing Maven Projects**.
5. Browse to the extracted `resource-booking-system` folder.
6. Select `pom.xml`.
7. Click **Finish**.
8. Wait for Maven dependencies to finish downloading.
9. Right-click the project → **Run As → Spring Boot App**.

If STS reports stale Maven dependencies, right-click the project → **Maven → Update Project**.

## 4. Seed users

On startup, the application creates these accounts if they do not already exist:

| Role | Username | Password |
|---|---|---|
| ADMIN | `admin` | `Admin@123` |
| USER | `user` | `User@123` |

Passwords are stored using BCrypt. Change the seed credentials before using the application outside local testing.

## 5. Authentication

### Login

`POST /auth/login`

```json
{
  "username": "user",
  "password": "User@123"
}
```

The response contains a JWT. Send it on protected endpoints as:

```text
Authorization: Bearer <token>
```

## 6. Main endpoints

### Authentication

- `POST /auth/login` — public login

### Resources

- `GET /api/resources` — ADMIN and USER
- `GET /api/resources/{id}` — ADMIN and USER
- `POST /api/resources` — ADMIN only
- `PUT /api/resources/{id}` — ADMIN only
- `DELETE /api/resources/{id}` — ADMIN only

### Reservations

- `GET /api/reservations` — ADMIN sees all; USER sees only their own
- `GET /api/reservations/{id}` — ADMIN can view any; USER can view only their own
- `POST /api/reservations` — USER creates a reservation for themselves
- `PUT /api/reservations/{id}` — ADMIN can update any; USER can update their own
- `DELETE /api/reservations/{id}` — ADMIN can delete any; USER can delete their own

The USER reservation request **does not contain `userId`**. Ownership comes from the authenticated JWT/security context.

## 7. Reservation filtering, pagination and sorting

`GET /api/reservations?status=PENDING&minPrice=100&maxPrice=1000&page=0&size=10&sortBy=startTime&direction=desc`

Supported filters:

- `status`: `PENDING`, `CONFIRMED`, `CANCELLED`
- `minPrice`
- `maxPrice`
- `page` (zero-based)
- `size`
- `sortBy`
- `direction`: `asc` or `desc`

## 8. Validation

The API validates required fields, positive/valid prices, reservation status, and start/end times. Reservation creation also prevents overlapping bookings for the same resource.

## 9. Swagger/OpenAPI

After starting the application:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 10. Postman

Import `postman_collection.json` into Postman. Login first and copy the returned JWT into the collection variable `token`.

## 11. Project structure

```text
src/main/java/com/example/booking
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
└── specification
```

## 12. Testing

Run from STS using **Run As → JUnit Test**, or from a terminal:

```bash
mvn clean test
```

The test suite includes JWT/security-related tests. Add integration tests with `MockMvc`/a test database when extending the project.

## 13. Important production notes

- Never commit real database passwords or JWT secrets.
- Set a long random `JWT_SECRET` through the environment.
- Use HTTPS in production.
- Replace the demo seed passwords before deployment.
- Consider Flyway or Liquibase instead of `ddl-auto=update` for production database migrations.
