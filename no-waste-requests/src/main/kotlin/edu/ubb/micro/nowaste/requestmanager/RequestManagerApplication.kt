package edu.ubb.micro.nowaste.requestmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class RequestManagerApplication

fun main(args: Array<String>) {
	runApplication<RequestManagerApplication>(*args)
}
