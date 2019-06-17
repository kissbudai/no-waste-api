package edu.ubb.micro.nowaste.productmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ProductManagerApplication

fun main(args: Array<String>) {
	runApplication<ProductManagerApplication>(*args)
}
