# EcoTrekker REST API

# To test the Endpoint

```powershell
curl http://localhost:8080/v1/calc/co2 -X POST -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"routes\":[{\"steps\":[{ \"vehicle\" : \"/metro\", \"line\" : \"U8\", \"start\" : \"S+U Hermannstr. (Berlin)\", \"end\" : \"S+U Jannowitzbrücke (Berlin)\" },{\"vehicle\":\"/bike/e-bike\",\"distance\":1500}]}]}'
```

```powershell
curl http://localhost:8080/v1/calc/co2 -X POST -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"gamification\":true,\"routes\":[{\"steps\":[{ \"vehicle\" : \"/metro\", \"line\" : \"U8\", \"start\" : \"S+U Hermannstr. (Berlin)\", \"end\" : \"S+U Jannowitzbrücke (Berlin)\" },{\"vehicle\":\"/bike/e-bike\",\"distance\":1500}]}]}'
```