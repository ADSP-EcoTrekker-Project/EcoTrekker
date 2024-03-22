## Development

While developing we recommend using [minikube](https://minikube.sigs.k8s.io/docs/) or [kind](https://kind.sigs.k8s.io/).
Or our `start.sh` script to start it locally in docker.

## Deploying

### Locally using jar files (Experimental)

In the current directory we provide the [play.sh](./play.sh) script to start all build and start all seven services.
Each service is started as a separate subprocess.
Due to problems of detecting the process ids of the started JVMs, exiting the script will not terminate the services.
Instead they will be running and have to be stopped manually or by restarting your machine.

### Locally using Docker

If you have [install](https://docs.docker.com/engine/install/)ed docker you can use the `start.sh` script to start all seven services as docker containers.
If you use docker engine with Docker Desktop on Windows you can use the script as is, if you use linux instead, you have to replace the addresses `172.17.0.1` with `127.0.0.1`.

### using helm

If you have installed minikube or kind or have a functioning kubernetes cluster available, you can use helm to install EcoTrekker.
From the `chart/` directory just execute the following commands:

```powershell
# Install chart with default values
setup/chart> helm install ecotrekker .
# wait for all pods to start
> kubectl get pod -A --watch
# port forward the rest api pod
> kubectl port-forward -n ecotrekker <pod-name-of-deployment> 8080:8080
```

After that EcoTrekker can be reached using `127.0.0.1:8080`

### using Terraform (Difficult)

To use the terraform module, you need to setup a Google Cloud Project, activate the Compute Engine and Kubernetes Engine APIs.
You also need to create a new service account for the project and download a key in `.json` format.
This key needs to be put in the `terraform/` directory and the name and path to the key in the main.tf file needs to be updated.

Furthermore, you need to change the ecotrekker.rest-api.ingress entry to a domain / subdomain you have access too.
Alternatively, to connect you can also download a kubectl config file to access the cluster with kubectl from you local machine.
In that case you can again use port-forward like above.

Now you can execute `terraform apply`.
This will create a new kubernetes cluster consisting of two `e2-highcpu-4` VMs.

After terraform finished (should take around 15 minutes), you need to head to Kubernetes Engine under "Network" go to "Gateways, Services & Ingress" and there to the "Ingress" tab.
There you will find the ingress pointing to your domain.
Click on it to find the static IP-Address and create an entry at the name server for your domain.

After a few minutes, you should be able to reach EcoTrekker under "http://you-domain.com".