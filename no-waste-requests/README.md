# Product Manager Microservice.

This microservice is responsible for interactions with the Request domain objects.

Microservice name for eureka server: `request-manager-service`.


## Getting started

* Every route is protected with JWT `Bearer` header. The `Authorization` header is used to identify the user for some of the actions
which are related to a specific user.

### As standalone service

* The project can be built as a standalone service with `gradle bootRun` and by default, it can be accessed on `localhost`, on 
port `8004`.

### Part of the no-waste system.
* When running, it will try to connect to an Eureka server located on `localhost`, on port `8004`.
* The docker image of this service can be built with `gradle jibDockerBuild` command.
* To Kubernetes, it can be deployed by running `kubectl create -f kubernetes/`.
* It will run on localhost, on `8004` internal, container port. And for testing purposes, the service can be accessed on port `8104` from outside
 if the service is deployed as well to kubernetes. 
 

## Endpoints

* Test endpoint: GET /requests/health: Returns a static test message signaling that the microservice is running and can be accessed.

* GET /requests : with `page` and `per_page` parameters. Returns all requests for the user who performs the action.
* GET /requests/:id : Return the request for the `{id}`.
* POST /requests : with `id` (product id) param. Creates a new request for the product defined by the `id` parameter. Also notifies the
`product-manager-service` about this request to update the status of the requested product.
