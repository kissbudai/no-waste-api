package edu.ubb.micro.nowaste.usermanager.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceController {

	@RequestMapping("/users/health")
	fun getServiceHealth(): ResponseEntity<String> = ResponseEntity.ok("User manager up and running.")
}