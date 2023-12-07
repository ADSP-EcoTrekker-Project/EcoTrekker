#FROM eclipse-temurin:17-jdk-jammy AS build
FROM gradle:8.4.0-jdk17-jammy AS builder

ENV BUILD_HOME=/ecotrekker/build
WORKDIR ${BUILD_HOME}

#Environment Variables required for Building
#Environment Variables required for Testing
ENV KAFKA_BOOTSTRAP_SERVERS=localhost:8080

COPY . .
RUN gradle clean build