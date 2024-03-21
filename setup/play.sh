#!/bin/bash
pushd ..

jobs=()
trap 'echo "Stopping... ${jobs[@]} / $jobs / ${jobs} / ${jobs[*]}"; kill -9 ${jobs[*]}; sleep 5; kill -9 0' 1 2 3 15

gradle clean
# gradle test
gradle bootJar

(java -jar vehicle-depot/build/libs/*.jar --server.port=8087 --config=vehicle-depot/depot-config/germany.toml) & jobs+=($!)

(java -jar grid-co2-cache/build/libs/*.jar --server.port=8086) & jobs+=($!)

(java -jar vehicle-consumption/build/libs/*.jar --server.port=8085 --configPath=vehicle-config/vehicles.toml) & jobs+=($!)

(java -jar co2-calculator/build/libs/*.jar --server.port=8083 --consumption-service.address=http://127.0.0.1:8085 --grid-co2-cache.address=http://127.0.0.1:8086 --depot-service.address=http://127.0.0.1:8087) & jobs+=($!)

(java -jar public-transport-distance/build/libs/*.jar --server.port=8084 --configPath=public-transport-distance/data-config/data_modified_imputed.json) & jobs+=($!)
 
(java -jar route-manager/build/libs/*.jar --server.port=8081 --distance-service.address=http://127.0.0.1:8084 --co2-service.address=http://127.0.0.1:8083) & jobs+=($!)

(java -jar rest-api/build/libs/*.jar --server.port=8080 --route-service.address=http://127.0.0.1:8081) & jobs+=($!)

echo ${jobs[@]}

popd

wait

# sleep 20

# read -p "Press any Key to stop..."

# kill 0