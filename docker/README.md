This directory contains the Dockerfiles required to build each microservice and to build the containers in which the services are later deployed.

## Overview

The builder.Dockerfile is the dockerfile used to execute the gradle build of each service.
The resulting container is used in the other Dockerfiles to provide the context from which the packaged applications are copied.

### Microservice Dockerfiles

Each Dockerfile is named after the subdirectory of their microservice.

- rest-api.Dockerfile
- co2-calculator.Dockerfile
- grid-co2-cache.Dockerfile
- vehicle-consumption.Dockerfile
- vehicle-depot.Dockerfile