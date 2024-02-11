terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "5.10.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "3.6.0"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "2.25.1"
    }
    helm = {
      source  = "hashicorp/helm"
      version = "2.12.1"
    }
  }
}

provider "google" {
  credentials = file("adsp-ecotrekker-6fc9257fae68.json")

  project = "adsp-ecotrekker"
  region  = "europe-west3"
  zone    = "europe-west3-a"
}

provider "kubernetes" {
  host  = "https://${google_container_cluster.ecotrekker.endpoint}"
  token = data.google_client_config.current.access_token
  cluster_ca_certificate = base64decode(
    google_container_cluster.ecotrekker.master_auth[0].cluster_ca_certificate,
  )
}

provider "helm" {
  kubernetes {
    host  = "https://${google_container_cluster.ecotrekker.endpoint}"
    token = data.google_client_config.current.access_token
    cluster_ca_certificate = base64decode(
      google_container_cluster.ecotrekker.master_auth[0].cluster_ca_certificate,
    )
  }
}

data "google_client_config" "current" {
}

resource "terraform_data" "always_run" {
  input = timestamp()
}