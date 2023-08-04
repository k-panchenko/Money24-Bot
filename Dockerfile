# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY . .

CMD ["./gradlew", "bootRun"]