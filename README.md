# Todo Backend – Spring Boot API

This is the backend service for the Todo Application.
It provides REST APIs to create tasks, list recent open tasks, and mark tasks as completed.

### 🚀 Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Hibernate
* Docker
* JUnit 5 + Mockito
* Swagger (OpenAPI 3)

### ⚙️ Prerequisites

Make sure you have installed:

* Java 17+
* Maven
* MySQL
* Docker
* IntelliJ IDEA

### 🔧 Configuration

Backend uses application.properties.

Example:

spring.datasource.url=jdbc:mysql://localhost:3306/tododb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

**Swagger**

springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true

### ▶️ Running Backend Locally (Without Docker)

##### 1. Create MySQL database

   CREATE DATABASE todo_db;

2. ##### Update your DB credentials in application.properties.

##### 3. Run application:

   mvn spring-boot:run


#### App runs at:

http://localhost:8080


Swagger UI:

http://localhost:8080/swagger-ui/index.html

### 🐳 Running with Docker

Ensure Docker Desktop is installed.

1. Build JAR
   mvn clean package -DskipTests

2. Build Docker image
   docker build -t todo-backend .

3. Start with docker-compose (recommended)

From the root directory containing docker-compose.yml:

docker-compose up --build


Backend will run inside the container at:

http://localhost:8080

### 🧪 Running Tests

mvn test

This runs:

Controller tests (MockMvc)

Service tests (Mockito)

### 🎉 API Endpoints

POST	/api/tasks - Create task

GET	/api/tasks?limit=X - List tasks

POST	/api/tasks/{id}/complete - Complete task