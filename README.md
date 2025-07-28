# User Service - AccessFlow

The **User Service** is a microservice in the AccessFlow platform, responsible for managing user-related data and operations. It supports CRUD operations, validation, role assignment, and integration with other services in the ecosystem via Kafka and REST.

---

## 🔧 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Validation
- H2 / SQLite (or your DB)
- Kafka (for inter-service communication)
- RESTful APIs
- Maven / Gradle

---

## 📁 Project Structure

src/
├── main/
│ ├── java/com/accessflow/user/
│ │ ├── controller/
│ │ ├── dto/
│ │ ├── entity/
│ │ ├── exception/
│ │ ├── repository/
│ │ ├── service/
│ │ ├── config/
│ ├── resources/
│ ├── application.properties

pgsql
Copy
Edit

---

## 🚀 Features

- Create, Read, Update, Delete (CRUD) users
- Input validation using `@Valid` annotations
- Global exception handling with custom error messages
- DTO usage to prevent exposing internal entities
- Kafka integration for event publishing (optional)
- Role-based attribute for user classification

---

## 🧪 API Endpoints

### Base URL: `/api/users`

| Method | Endpoint           | Description                  |
|--------|--------------------|------------------------------|
| GET    | `/`                | Get all users                |
| GET    | `/{id}`            | Get user by ID               |
| POST   | `/`                | Create a new user            |
| PUT    | `/{id}`            | Update an existing user      |
| DELETE | `/{id}`            | Delete a user by ID          |

---

## 📦 Request Payload Example

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "USER"
}
🛡️ Validation
Uses JSR-380 (javax.validation) annotations

Examples:

@NotBlank for name and email

@Email for email format

@Pattern for role if specific values are expected

⚠️ Exception Handling
Global handler using @ControllerAdvice

Custom exceptions for:

Resource not found

Validation failures

Duplicate users (if implemented)

🧰 How to Run
Pre-requisites:
Java 17+

Maven or Gradle

Steps:
bash
Copy
Edit
# Clone the repository
git clone https://github.com/your-repo/user-service.git

# Navigate into the project
cd user-service

# Build and run
./mvnw spring-boot:run
📡 Kafka Integration (Optional)
Publishes user-related events (e.g., user-created) to a Kafka topic.

You can configure Kafka settings in application.properties.

⚙️ Configuration
Update application.properties:

properties
Copy
Edit
server.port=8000
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
# Kafka and other config...

