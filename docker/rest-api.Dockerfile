FROM bake-base-image as builder

FROM eclipse-temurin:17-jdk-jammy

VOLUME [ "/tmp" ]
COPY --from=builder /ecotrekker/build/rest-api/build/libs/*.jar app.jar

EXPOSE 8080

ENV KAFKA_BOOTSTRAP_SERVERS=localhost:9092

ENTRYPOINT ["java", "-jar", "/app.jar", "-Dspring.kafka.producer.bootstrap.servers=${KAFKA_BOOTSTRAP_SERVERS}"]