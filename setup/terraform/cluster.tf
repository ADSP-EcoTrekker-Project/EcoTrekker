resource "google_container_cluster" "ecotrekker" {
  name     = "ecotrekker-cluster"
  location = "europe-west3"

  deletion_protection = false

  # We can't create a cluster with no node pool defined, but we want to only use
  # separately managed node pools. So we create the smallest possible default
  # node pool and immediately delete it.
  remove_default_node_pool = true
  initial_node_count       = 1

  lifecycle {
    ignore_changes = [
      initial_node_count
    ]
  }
}

resource "google_container_node_pool" "ecotrekker_nodes" {
  name           = "ecotrekker-node-pool"
  location       = "europe-west3"
  node_locations = ["europe-west3-a"]
  cluster        = google_container_cluster.ecotrekker.name
  node_count     = 1

  node_config {
    preemptible  = true
    machine_type = "e2-medium"

    # Google recommends custom service accounts that have cloud-platform scope and permissions granted via IAM Roles.
    service_account = google_service_account.ecotrekker.email
    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }

  lifecycle {
    ignore_changes = [
      initial_node_count
    ]
  }
}