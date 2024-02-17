FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /ecotrekker/build/rest-api/build/libs/*.jar app.jar

EXPOSE 8080

ENV ROUTE_SERVICE_ADDRESS=http://localhost:8081

ENTRYPOINT ["java", "-jar", "app.jar", "--route-service.address=${ROUTE_SERVICE_ADDRESS}"]