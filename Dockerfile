# syntax=docker/dockerfile:1

FROM openjdk:17-eclipse-temurin

WORKDIR /app

COPY . .

CMD ["./gradlew", "bootRun"]