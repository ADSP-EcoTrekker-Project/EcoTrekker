FROM bake-base-image as builder



FROM eclipse-temurin:17-jdk-jammy

VOLUME [ "/tmp" ]
COPY --from=builder /ecotrekker/build/co2-calculator/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]