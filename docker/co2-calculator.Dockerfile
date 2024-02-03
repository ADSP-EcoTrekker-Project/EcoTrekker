FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

VOLUME [ "/tmp" ]
COPY --from=builder /ecotrekker/build/co2-calculator/build/libs/*.jar app.jar

EXPOSE 8080

ENV CONSUMPTION_SERVICE_ADDRESS=http://localhost:8085

ENTRYPOINT ["java", "-jar", "/app.jar", "--consumption-service.address=${CONSUMPTION_SERVICE_ADDRESS}"]