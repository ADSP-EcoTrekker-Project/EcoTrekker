curl -o NUL -s -w "@curl-format.txt" http://localhost:8080/v1/calc/co2 -i -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"routes\":[{\"steps\":[{\"vehicle\":\"car\",\"distance\":300},{\"vehicle\":\"bike\",\"distance\":500}]},{\"steps\":[{\"vehicle\":\"bicycle\",\"distance\":2000}]}]}'