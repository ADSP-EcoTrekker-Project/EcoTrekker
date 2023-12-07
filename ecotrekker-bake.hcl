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

group "default" {
    targets = ["rest-api", "co2-calculator"]
}