# EcoTrekker

## Services

### REST API

[here](rest-api/README.md)

### CO2 Calculator

[here](co2-calculator/README.md)

## Containers

Environment Variables (Powershell): 
```powershell
$env:REPO="lierseleow"
```

```powershell
docker buildx bake -f .\ecotrakker-bake.hcl
```

## Building with gradle

Currently, there may be a bug, where the Embedded Kafka Broker used for testing will not start up when using `gradle build` after `gradle clean`.
In that case, just use `gradle build` a second time (without `clean`)
