FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /ecotrekker/build/vehicle-depot/build/libs/*.jar app.jar

COPY --from=builder /ecotrekker/build/vehicle-depot/depot-config/. .

EXPOSE 8080

ENV SERVICE_CONFIG_LOCATION=berlin.toml

ENTRYPOINT ["java", "-jar", "app.jar", "--config=${SERVICE_CONFIG_LOCATION}"]