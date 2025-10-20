FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get clean
WORKDIR /app
COPY build/libs/SH-BackendService.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]