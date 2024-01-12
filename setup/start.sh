#!/bin/sh

if [[ -z ${REPO} ]]; then
    REPO="lierseleow"
    echo "Set REPO Environment Variable to default value"
fi

docker run -d --restart unless-stopped -p 8082:8080 \
    ${REPO}/eco-vehicle-consumption:latest

docker run -d --restart unless-stopped -p 8081:8080 \
    -e CONSUMPTION_SERVICE_ADDRESS="172.17.0.1:8082" \
    ${REPO}/eco-co2-calculator:latest

docker run -d --restart unless-stopped -p 8080:8080 \
    -e CO2_CALCULATOR_ADDRESS="172.17.0.1:8081" \
    ${REPO}/eco-rest-api:latest