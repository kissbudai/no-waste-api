package edu.ubb.micro.nowaste.productmanager

import edu.ubb.micro.nowaste.productmanager.mock.MockDataGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ProductManagerApplication(mockDataGenerator: MockDataGenerator) {

	init {
		mockDataGenerator.generate()
	}
}

fun main(args: Array<String>) {
	runApplication<ProductManagerApplication>(*args)
}
