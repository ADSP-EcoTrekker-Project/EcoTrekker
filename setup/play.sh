#!/bin/bash

if [[ -z ${REPO} ]]; then
    REPO="lierseleow"
    echo "Set REPO Environment Variable to default value"
fi

pushd ..

# gradle clean
# gradle test
gradle bootJar

bash -c "java -jar vehicle-depot/build/libs/*.jar --server.port=8087 --config=vehicle-depot/depot-config/germany.toml" &

bash -c "java -jar grid-co2-cache/build/libs/*.jar --server.port=8086" &

sleep 15

read -p "Press any Key to stop..."

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
#     -e GAMIFICATION_SERVICE_ADDRESS="172.17.0.1:8082" \
#     ${REPO}/eco-route-manager:latest

# docker run -d --restart unless-stopped -p 8080:8080 \
#     -e ROUTE_SERVICE_ADDRESS="172.17.0.1:8081" \
#     ${REPO}/eco-rest-api:latest