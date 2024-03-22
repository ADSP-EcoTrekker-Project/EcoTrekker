# EcoTrekker

## How to Contribute

More information on how to contribute can be found [here](./CONTRIBUTION.md)

## Services

### REST API

[here](rest-api/README.md)

### Route Manager

[here](./route-manager/README.md)

### Public Transport Distance

[here](./public-transport-distance/README.md)

### Calculator

[here](co2-calculator/README.md)

### Vehicle Consumption

[here](./vehicle-consumption/README.md)

### Vehicle Depot

[here](./vehicle-depot/README.md)

## Building Containers

### Dockerfiles

More info about the Dockerfiles can be found [here](./docker/README.md)

### The Bake file

The [ecotrekker-bake.hcl](./ecotrekker-bake.hcl) file contains the instructions on how to build and use the different Dockerfiles.
After executing the bakefile your local docker repository will have all seven required images available.
Alternatively they will be available at the `lierseleow` repository on dockerhub for some time.

The following variables can be set:

- $REPO - which repository to use; defaults to 'ecotrekker'
- $TAG - which tag to give the resulting containers; defaults to 'latest'

### How to build

1. Set the Environment Variables

#### Powershell

```powershell
$env:REPO="your_amazing_repository"
```

### Bash

```bash
export REPO="your_amazing_repository"
```

2. Execute the bake file 
```powershell
docker buildx bake -f .\ecotrekker-bake.hcl
```

## Starting EcoTrekker

A thorough guide on how to deploy EcoTrekker can be found [here](./setup/README.md)