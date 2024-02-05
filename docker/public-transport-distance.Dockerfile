FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /ecotrekker/build/public-transport-distance/build/libs/*.jar app.jar

COPY --from=builder /ecotrekker/build/public-transport-distance/data-config/. .

EXPOSE 8080

ENV PUBLIC_TRANSPORT_DATA_LOCATION=data_modified_imputed.json

ENTRYPOINT ["java", "-jar", "app.jar", "--configPath=${PUBLIC_TRANSPORT_DATA_LOCATION}"]