package edu.ubb.micro.nowaste.productmanager

import edu.ubb.micro.nowaste.productmanager.service.ampq.AmpqReceiverService
import edu.ubb.micro.nowaste.productmanager.mock.MockDataGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 * [AmpqReceiverService] has to be inject here in order to initialize the AMPQ listeners.
 */
@SpringBootApplication
@EnableEurekaClient
class ProductManagerApplication(mockDataGenerator: MockDataGenerator, ampqReceiverService: AmpqReceiverService) {

	init {
		mockDataGenerator.generate()
	}
}

fun main(args: Array<String>) {
	runApplication<ProductManagerApplication>(*args)
}
