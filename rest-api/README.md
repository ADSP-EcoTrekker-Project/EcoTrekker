# EcoTrekker REST API

# To test the Endpoint

```powershell
curl http://localhost:8080/v1/calc/co2 -X POST -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"routes\":[{\"steps\":[{\"vehicle\":\"car\",\"distance\":1000},{\"vehicle\":\"e-bike\",\"distance\":1500}]}]}'
```