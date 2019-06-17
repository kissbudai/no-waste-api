package edu.ubb.micro.nowaste.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class TestApplication

fun main(args: Array<String>) {
	runApplication<TestApplication>(*args)
}
