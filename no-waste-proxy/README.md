# Zuul Proxy for No-waste Microservice System.

This microservice represents the entry to point to No-waste microsystem ecosystem.
This [Zuul](https://github.com/Netflix/zuul) Service represents the front door for all reuqests which handles the dynamic routing of the requests.

## Getting started

### As standalone service

* The project can be built as a standalone service with `gradle bootRun` and by default, it can be accessed on `localhost`, on 
port `8762`.

### Part of the no-waste system, on Kubernetes.
* The docker image of this service can be built with `gradle jibDockerBuild` command.
* To Kubernetes, it can be deployed by running `kubectl create -f kubernetes/`.
* It will run on localhost, on `8762` internal, container port and from outside, it can be accessed on port `8762` after the service is deployed.

## Endpoints

* The front door to every internal service is through `/api/...` endpoints and `Zuul` will route the request to the available microservice 
who can handle the requested action.