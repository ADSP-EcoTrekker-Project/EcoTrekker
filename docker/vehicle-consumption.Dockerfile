FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /ecotrekker/build/vehicle-consumption/build/libs/*.jar app.jar

COPY --from=builder /ecotrekker/build/vehicle-config/. .

EXPOSE 8080

ENV SERVICE_CONFIG_LOCATION=vehicles.toml

ENTRYPOINT ["java", "-jar", "app.jar", "--configPath=${SERVICE_CONFIG_LOCATION}"]