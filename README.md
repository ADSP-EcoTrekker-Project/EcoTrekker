# EcoTrekker

## Services

### REST API

[here](rest-api/README.md)

### CO2 Calculator

[here](co2-calculator/README.md)


## Building Containers

### Dockerfiles

More info about the Dockerfiles can be found [here](docker/README.md)

### The Bake file

The [ecotrekker-bake.hcl](ecotrekker-bake.hcl) file contains the instructions on how to build and use the different Dockerfiles.

The following variables can be set:

- $REPO - which repository to use; defaults to 'ecotrekker'
- $TAG - which tag to give the resulting containers; defaults to 'latest'

### How to build

Environment Variables (Powershell):

```powershell
$env:REPO="your_amazing_repository"
```

```powershell
docker buildx bake -f .\ecotrakker-bake.hcl
```
