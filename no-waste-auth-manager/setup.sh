kubectl delete service no-waste-auth-manager-service
kubectl delete deployment no-waste-auth-manager-deployment

gradle jibDockerBuild

kubectl create -f kubernetes/