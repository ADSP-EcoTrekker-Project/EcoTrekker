
## Test Endpoint

```powershell
curl -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{ \"vehicle\" : \"metro\", \"line\" : \"U8\", \"start\" : \"S+U Hermannstr. (Berlin)\", \"end\" : \"S+U Jannowitzbrücke (Berlin)\" }' 'localhost:8080/v1/calc/distance'
``` 