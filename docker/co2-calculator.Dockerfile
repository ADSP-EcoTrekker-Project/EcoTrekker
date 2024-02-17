FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /ecotrekker/build/co2-calculator/build/libs/*.jar app.jar

EXPOSE 8080

ENV CONSUMPTION_SERVICE_ADDRESS=http://localhost:8085
ENV GRID_CO2_SERVICE_ADDRESS=http://localhost:8086
ENV VEHICLE_DEPOT_SERVICE_ADDRESS=http://localhost:8087

ENTRYPOINT ["java", "-jar", "app.jar", "--consumption-service.address=${CONSUMPTION_SERVICE_ADDRESS}", "--grid-co2-cache.address=${GRID_CO2_SERVICE_ADDRESS}", "--depot-service.address=${VEHICLE_DEPOT_SERVICE_ADDRESS}"]