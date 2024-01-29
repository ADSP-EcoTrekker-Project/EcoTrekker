FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

VOLUME [ "/tmp" ]
COPY --from=builder /ecotrekker/build/grid-co2-cache/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]