package edu.ubb.micro.nowaste.authmanager.controller

import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceController(private val environment: Environment) {

	@RequestMapping("/auth/health")
	fun getServiceHealth(): ResponseEntity<String> =
		ResponseEntity.ok("Auth manager up and running on port ${environment.getProperty("local.server.port")}")
}