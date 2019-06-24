kubectl delete statefulset no-waste-eureka-deployment
kubectl delete service no-waste-eureka-service

gradle jibDockerBuild

kubectl create -f kubernetes/