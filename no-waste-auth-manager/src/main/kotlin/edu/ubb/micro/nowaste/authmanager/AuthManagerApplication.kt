package edu.ubb.micro.nowaste.authmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class AuthManagerApplication

fun main(args: Array<String>) {
	runApplication<AuthManagerApplication>(*args)
}