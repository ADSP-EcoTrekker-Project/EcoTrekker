
## Endpoint Testing

```powershell
curl http://localhost:8080/v1/calc/routes -X POST -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"routes\":[{\"steps\":[{\"vehicle\":\"car\",\"distance\":1000},{\"vehicle\":\"e-bike\",\"distance\":1500}]}]}'
```