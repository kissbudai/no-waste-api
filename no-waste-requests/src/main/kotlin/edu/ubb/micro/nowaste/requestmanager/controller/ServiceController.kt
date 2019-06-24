package edu.ubb.micro.nowaste.requestmanager.controller

import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceController(private val environment: Environment) {

	@RequestMapping("/requests/health")
	fun getServiceHealth(): ResponseEntity<String> =
		ResponseEntity.ok("Request manager up and running  at port ${environment.getProperty("local.server.port")}")
}