# Authentication Microservice.

This microservice is responsible for user authentication.

Microservice name for eureka server: `no-waste-auth-manager`.

## Getting started

### As standalone service

* The project can be built as a standalone service with `gradle bootRun` and by default, it can be accessed on `localhost`, on 
port `8001`.
* By default, none of the endpoints are secured with JWT filter

### Part of the no-waste system.
* When running, it will try to connect to an Eureka server located on `localhost`, on port `8761`.
* The docker image of this service can be built with `gradle jibDockerBuild` command.
* To Kubernetes, it can be deployed by running `kubectl create -f kubernetes/`.
* It will run on localhost, on `8001` internal, container port. And if the service is deployed to kubernetes as well, then the through the service, it can be accessed on port 
`8101`.

## Endpoints

* Test endpoint: GET /auth/health: Returns a static test message signaling that the microservice is running and can be accessed.

* GET /auth/user/me: Returns the user determined from the Authorization `Bearer` token.
* POST /auth/login: Authenticates a user with `username` and `password` and returns a `Bearer` token.
* POST /auth/register: Registers a new user into the system.
