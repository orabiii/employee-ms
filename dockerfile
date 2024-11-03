# Use an OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy all project files into the container
COPY employee-service/target/employee-service.jar employee-service.jar
COPY discovery-service/target/discovery-service.jar discovery-service.jar
COPY api-gateway/target/api-gateway.jar api-gateway.jar

# Expose ports for your services
EXPOSE 8080 8761

# Start the services
CMD ["sh", "-c", "java -jar employee-service.jar & java -jar discovery-service.jar & java -jar api-gateway.jar"]
