#FROM eclipse-temurin:17-jdk-jammy AS build
FROM gradle:8.4.0-jdk17-jammy AS builder

ENV BUILD_HOME=/ecotrekker/build
WORKDIR ${BUILD_HOME}

COPY . .
RUN gradle clean
RUN gradle build; exit 0
RUN gradle build 