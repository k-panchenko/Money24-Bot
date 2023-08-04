# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-apline

WORKDIR /app

COPY . .

CMD ["./gradlew", "bootRun"]