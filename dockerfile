FROM openjdk:17-jdk-slim

WORKDIR /app

COPY discovery-service-0.0.1-SNAPSHOT.jar /app/discovery-service-0.0.1-SNAPSHOT.jar
COPY api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway-0.0.1-SNAPSHOT.jar
COPY employee-service-0.0.1-SNAPSHOT.jar /app/employee-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080 8761

CMD ["sh", "-c", "java -jar /app/discovery-service-0.0.1-SNAPSHOT.jar & sleep 20 && java -jar /app/employee-service-0.0.1-SNAPSHOT.jar & java -jar /app/api-gateway-0.0.1-SNAPSHOT.jar"]
