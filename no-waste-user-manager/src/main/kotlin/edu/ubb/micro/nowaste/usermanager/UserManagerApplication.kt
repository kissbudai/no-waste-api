package edu.ubb.micro.nowaste.usermanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class UserManagerApplication

fun main(args: Array<String>) {
	runApplication<UserManagerApplication>(*args)
}
