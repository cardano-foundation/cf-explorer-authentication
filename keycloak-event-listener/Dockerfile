FROM openjdk:18-jdk-slim AS build
RUN apt update -qq && apt install -y gettext-base

WORKDIR /app
COPY pom.xml /app/pom.xml
COPY mvnw  /app/mvnw
COPY .mvn /app/.mvn
RUN ./mvnw verify clean --fail-never
COPY . /app
RUN ./mvnw clean package -DskipTests

FROM quay.io/keycloak/keycloak:latest

COPY --from=build /app/target/*.jar /opt/keycloak/providers
COPY ./imports /opt/keycloak/data/import
