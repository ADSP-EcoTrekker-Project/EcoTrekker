resource "helm_release" "ecotrekker" {
  depends_on = [google_container_cluster.ecotrekker, google_container_node_pool.ecotrekker_nodes, google_compute_global_address.ecotrekker-static-address]

  name  = "ecotrekker"
  chart = "../chart"

  atomic = true

  namespace        = "ecotrekker"
  create_namespace = true
}