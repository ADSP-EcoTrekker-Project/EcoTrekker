variable "REPO" {
    default = "ecotrekker"
}

variable "TAG" {
    default = "latest"
}

target "builder" {
    dockerfile = "docker/builder.Dockerfile"
    tags = ["${REPO}/eco-builder:latest"]
    context = "."
}

target "rest-api" {
    dockerfile = "../docker/rest-api.Dockerfile"
    tags = ["${REPO}/eco-rest-api:${TAG}"]
    contexts = {
        bake-base-image = "target:builder"
    }
    context = "rest-api/"
}

target "co2-calculator" {
    dockerfile = "../docker/co2-calculator.Dockerfile"
    tags = ["${REPO}/eco-co2-calculator:${TAG}"]
    contexts = {
        bake-base-image = "target:builder"
    }
    context = "co2-calculator/"
}

target "grid-co2-cache" {
    dockerfile = "../docker/grid-co2-cache.Dockerfile"
    tags = ["${REPO}/eco-grid-co2-cache:${TAG}"]
    contexts = {
        bake-base-image = "target:builder"
    }
    context = "grid-co2-cache/"
}

target "vehicle-consumption" {
    dockerfile = "../docker/vehicle-consumption.Dockerfile"
    tags = ["${REPO}/eco-vehicle-consumption:${TAG}"]
    contexts = {
        bake-base-image = "target:builder"
    }
    context = "vehicle-consumption/"
}

target "vehicle-depot" {
    dockerfile = "../docker/vehicle-depot.Dockerfile"
    tags = ["${REPO}/eco-vehicle-depot:${TAG}"]
    contexts = {
        bake-base-image = "target:builder"
    }
    context = "vehicle-depot/"
}

group "default" {
    targets = ["rest-api", "co2-calculator", "grid-co2-cache", "vehicle-consumption", "vehicle-depot"]
}