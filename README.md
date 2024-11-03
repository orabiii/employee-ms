# Employee Management Microservices

This project consists of three microservices built using Spring Boot 3.x:

## 1. API Gateway

- **Description**: The API Gateway is a microservice built with Spring Cloud, running on port **8080**. It serves as the front door for our application and implements cross-cutting authentication using JWT.
- **Server**: Developed on an embedded Netty server to support a reactive model, capable of handling a large number of requests.

### APIs
- **/authenticate**: 
  - Authenticates users via basic authentication (username and password) and returns a JWT token for subsequent requests.

### Components
- **Security Config**: Configures security chains for application APIs, utilizing RSA keys for JWT encoding/decoding.
- **Gateway Config**: Implements default routes for the employee microservices.

---

## 2. Discovery Service

- **Description**: A registry service built on Spring Cloud Eureka, running on port **8761**. This service manages communication between microservices.

---

## 3. Employee Service

- **Description**: This microservice handles the business logic for the application, implementing a set of RESTful endpoints. It runs on port **8000** and is registered with the discovery service under the name **EMPLOYEE-SERVICE**.

### Components
- **H2 In-Memory Database**: Configured as a relational database with scripts to populate data upon startup.
- **Hibernate Validations**: Provides validations for entered data.
- **Controller Advice**: Manages exceptions as a cross-cutting aspect.
- **Resource Files**: Supports multi-language validation messages.
- **Swagger**: Documents and visualizes the endpoints in the application.
  - **Access**: [Swagger UI](http://{host_ip}:8000/swagger-ui/index.html)

---

## How to Run

To run the microservices, ensure you have the JAR files for each microservice and start them in the following order from the base directory:

1. **Start the Discovery Service**:
   ```bash
   cd discovery-service/target
   java -jar discovery-service-0.0.1-SNAPSHOT.jar
   ```

2. **Start the API Gateway**:
   ```bash
   cd api-gateway/target
   java -jar api-gateway-0.0.1-SNAPSHOT.jar
   ```

3. **Start the Employee Service**:
   ```bash
   cd employee-service/target
   java -jar employee-service-0.0.1-SNAPSHOT.jar
   ```

---

## Endpoints

- **Authenticate**:
  - `http://{host_ip}:8080/authenticate`
  - Use basic authentication with username and password to retrieve a JWT token.

- **CRUD Endpoints**:
  - Exposed through the gateway.
  - Example:
    - `http://{host_ip}:8080/api/employees/1`
      - Retrieves employee details based on ID. A valid JWT token is mandatory for access.

---

## Docker

For best practices in deployment, consider building Docker containers for the three services, using either a Dockerfile or Docker Compose. This approach allows you to create images that can be run as containers and uploaded to a Docker registry for easy access and deployment.

Due to time and system resource constraints, Docker implementation will be addressed in a future update.