# Eureka Server for No-waste Microservice System.

This microservice holds the [Eureka Server](https://github.com/Netflix/eureka) which is responsible for collecting the other Microservices deployed to the no-waste namespace.

Microservice name for eureka server: `eureka-server`.

## Getting started

* The Eurake Dashboard can be accessed on `HOSTNAME/eurekawebui` endpoint.

### As standalone service

* The project can be built as a standalone service with `gradle bootRun` and by default, it can be accessed on `localhost`, on 
port `8761`.

### Part of the no-waste system, on Kubernetes.
* The docker image of this service can be built with `gradle jibDockerBuild` command.
* To Kubernetes, it can be deployed by running `kubectl create -f kubernetes/`.
* It will run on localhost, on `8761` internal, container port. And if the service is deployed to kubernetes as well, then the through the service, 
it can be accessed on port `8761`.