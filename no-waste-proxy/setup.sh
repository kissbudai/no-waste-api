kubectl delete service no-waste-proxy-service
kubectl delete deployment no-waste-proxy-deployment

gradle jibDockerBuild

kubectl create -f kubernetes/