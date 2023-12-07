# EcoTrekker REST API

# To test the Endpoint

```bash
curl http://localhost:8080/v1/calc/co2 -i -H '"Accept": application/json' -H '"Content-Type": application/json' -X GET -d '{\"routes\":[{\"steps\":[{\"start\":null,\"end\":null,\"vehicle\":null,\"distance\":null},{\"start\":null,\"end\":null,\"vehicle\":null,\"distance\":null}]}]}'
```