FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /ecotrekker/build/route-manager/build/libs/*.jar app.jar

EXPOSE 8080

ENV DISTANCE_SERVICE_ADDRESS=http://localhost:8084
ENV CO2_SERVICE_ADDRESS=http://localhost:8083
ENV GAMIFICATION_SERVICE_ADDRESS=http://localhost:8082

ENTRYPOINT ["java", "-jar", "app.jar", "--distance-service.address=${DISTANCE_SERVICE_ADDRESS}", "--co2-service.address=${CO2_SERVICE_ADDRESS}", "--gamification-service.address=${GAMIFICATION_SERVICE_ADDRESS}"]