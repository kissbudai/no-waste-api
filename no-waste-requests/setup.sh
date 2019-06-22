kubectl delete service no-waste-request-manager-service
kubectl delete deployment no-waste-request-manager-deployment

gradle jibDockerBuild

kubectl create -f kubernetes/