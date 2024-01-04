# Vehicle Consumption Service

## Config Format

Read more about the config format [here](../docs/VEHICLE%20CONFIG%20FORMAT.md)

## Test Endpoint using CURL

```powershell
curl localhost:8080/v1/consumption -X POST -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"vehicle_name\":\"car\"}'
```