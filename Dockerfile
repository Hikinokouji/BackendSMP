FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get clean
WORKDIR /app
COPY build/libs/BackendSMP-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]