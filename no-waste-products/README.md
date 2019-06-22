# Product Manager Microservice.

This microservice is responsible for interactions with the Product domain objects.

Microservice name for eureka server: `product-manager-service`.

## Getting started

* None of the routes are secured as this microservice shouldn't be accessible from the outside word. Every communication should be 
made through the Zuul proxy None of the endpoints are secured with this service.

### As standalone service

* The project can be built as a standalone service with `gradle bootRun` and by default, it can be accessed on `localhost`, on 
port `8003`.

### Part of the no-waste system.
* When running, it will try to connect to an Eureka server located on `localhost`, on port `8761`.
* The docker image of this service can be built with `gradle jibDockerBuild` command.
* To Kubernetes, it can be deployed by running `kubectl create -f kubernetes/`.
* It will run on localhost, on `8003` internal, container port. And for testing purposes, the service can be accessed on port `8103` from outside
 if the service is deployed as well to kubernetes. 
* !!! Deploying the service exposes the data to the outside world. !!!

## Endpoints

* Test endpoint: GET /products/health: Returns a static test message signaling that the microservice is running and can be accessed.

* GET /products : with `page` and `per_page` parameters. Returns the existing products (only those, which are `OPEN`).
* GET /products/:id : Return the product for the `{id}`.
* POST /products : Creates a new product with the user who performs the action. The user object will be requested from 
`auth-manager-service` service.
