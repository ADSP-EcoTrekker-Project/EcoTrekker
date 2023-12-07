## Development

While developing we recommend using [minikube](https://minikube.sigs.k8s.io/docs/) or [kind](https://kind.sigs.k8s.io/)

## Quick Setup using helm

```powershell
# Start development environment
> minikube start
# Install chart with default values
setup/chart> helm install ecotrekker .
# wait for all pods (deployment + kafka) to start
> kubectl get pod -A --watch
# port forward the deployment
> kubectl port-forward -n ecotrekker <pod-name-of-deployment> 8080:8080
# send test request using curl (on windows you may have a problem because curl is the standard alias for a windows utility which does not support all options used)
> curl http://localhost:8080/v1/calc/co2 -i -H '"Accept": application/json' -H '"Content-Type": application/json' -d '{\"routes\":[{\"steps\":[{\"start\":null,\"end\":null,\"vehicle\":null,\"distance\":null},{\"start\":null,\"end\":null,\"vehicle\":null,\"distance\":null}]}]}'
```