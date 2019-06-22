kubectl delete service no-waste-product-manager-service
kubectl delete deployment no-waste-product-manager-deployment
kubectl delete deployment products-redis-deployment

gradle jibDockerBuild

kubectl create -f kubernetes/