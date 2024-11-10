# Employee Microservices

This project consists of three microservices built using Spring Boot 3.x:

## 1. API Gateway

- **Description**: The API Gateway is a microservice built with Spring Cloud, running on port **8080**. It serves as the front door for our application and implements cross-cutting authentication using JWT.
- **Server**: Developed as an embedded Netty server to support a reactive model, capable of handling a large number of requests.

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
- **H2 In-Memory Database**: Configured as a relational database with database script on the resources folder to populate data upon startup.
- **Hibernate Validations**: Provides validations for entered data.
- **Custom Validations**: Provides custom validations for attributes on the employee class.
- **Controller Advice**: Manages exceptions as a cross-cutting aspect.
- **Resource Files**: Supports multi-language validation messages.
- **Locale**: Determine the messages languages (EN,AR) depends on the header parameter **Accept-Language**.
- **Swagger**: Documents and visualizes the endpoints in the application.
  - **Access**:`http://{host_ip}:8000/swagger-ui/index.html`

---

## How to Run

To run the microservices, ensure you have the JAR files for each microservice.

### build the jar files

 - cd to the microservice.
 - Run 
 ```bash
   mvn clean install -DskipTests
   ```


### Start the microservices in the following order. 

From the base directory wherevyou have downloaded the project:
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

To ensure best practices in deployment, we recommend building Docker containers for the three services using either a Dockerfile or Docker Compose. This approach enables the creation of Docker images that can be executed as containers and uploaded to a Docker registry for easy access and deployment.

### Dockerfile

A Dockerfile is provided to build an image containing the three microservices. Once built, this image is uploaded to the Docker Hub repository `orabiii/demodocker`.

#### How to Run the Image

**Step 1: Pull the Image**

```bash
docker pull orabiii/demodocker
```

**Step 2: Run the Container**

You should map the ports specified in your Dockerfile (8080 for the application services and 8761 for the discovery service) to your local machine. This can be achieved with the following command:

```bash
docker run -d -p 8080:8080 -p 8761:8761 orabiii/demodocker
```

## Updates
This section highlights the new features and components added to the project.

### Centeralized Config Server
A Spring Cloud-based configuration server is set up, which references a Git repository. This centralized config server manages the configurations for all the microservices in the project. It provides a consistent way to handle and update configuration properties across all services.

### OpenFeign
OpenFeign is integrated as the HTTP client to enable communication between microservices. It is implemented in the API Gateway, allowing seamless communication with the Employee microservice. This setup simplifies making REST calls between services while leveraging declarative HTTP client features.

### Unit Test
Unit tests have been implemented in the Employee microservice using JUnit and Mockito,
ensuring the stability units of the application. Mockito is used to mock dependencies testing.

### Integration Test
Integration tests are included to demonstrate the testing of a complete endpoint in the Employee microservice. These tests verify that multiple components work together as expected.


