#!/bin/bash

pushd ..

gradle clean
# gradle test
gradle bootJar

bash -c "java -jar vehicle-depot/build/libs/*.jar --server.port=8087 --config=vehicle-depot/depot-config/germany.toml" &

bash -c "java -jar grid-co2-cache/build/libs/*.jar --server.port=8086" &

bash -c "java -jar vehicle-consumption/build/libs/*.jar --server.port=8085 --configPath=vehicle-config/vehicles.toml" &

bash -c "java -jar co2-calculator/build/libs/*.jar --server.port=8083 --consumption-service.address=http://127.0.0.1:8085 --grid-co2-cache.address=http://127.0.0.1:8086 --depot-service.address=http://127.0.0.1:8087" &

bash -c "java -jar public-transport-distance/build/libs/*.jar --server.port=8084 --configPath=public-transport-distance/data-config/data_modified_imputed.json" &
 
bash -c "java -jar route-manager/build/libs/*.jar --server.port=8081 --distance-service.address=http://127.0.0.1:8084 --co2-service.address=http://127.0.0.1:8083" &

bash -c "java -jar rest-api/build/libs/*.jar --server.port=8080 --route-service.address=http://127.0.0.1:8081" &

sleep 15

read -p "Press any Key to stop..."

pkill -P $$

popd

# docker run -d --restart unless-stopped -p 8086:8080 \
#     ${REPO}/eco-grid-co2-cache:latest

# docker run -d --restart unless-stopped -p 8085:8080 \
#     ${REPO}/eco-vehicle-consumption:latest

# docker run -d --restart unless-stopped -p 8083:8080 \
#     -e VEHICLE_DEPOT_SERVICE_ADDRESS="172.17.0.1:8087" \
#     -e GRID_CO2_SERVICE_ADDRESS="172.17.0.1:8086" \
#     -e CONSUMPTION_SERVICE_ADDRESS="172.17.0.1:8085" \
#     ${REPO}/eco-co2-calculator:latest

# docker run -d --restart unless-stopped -p 8084:8080 \
#     ${REPO}/eco-public-transport-distance:latest

# docker run -d --restart unless-stopped -p 8082:8080 \
#     ${REPO}/eco-gamification:latest

# docker run -d --restart unless-stopped -p 8081:8080 \
#     -e DISTANCE_SERVICE_ADDRESS="172.17.0.1:8084" \
#     -e CO2_SERVICE_ADDRESS="172.17.0.1:8083" \
#     ${REPO}/eco-route-manager:latest

# docker run -d --restart unless-stopped -p 8080:8080 \
#     -e ROUTE_SERVICE_ADDRESS="172.17.0.1:8081" \
#     ${REPO}/eco-rest-api:latest