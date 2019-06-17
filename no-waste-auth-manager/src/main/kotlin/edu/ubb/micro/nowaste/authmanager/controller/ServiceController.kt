package edu.ubb.micro.nowaste.authmanager.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceController {

	@RequestMapping("/auth/health")
	fun getServiceHealth(): ResponseEntity<String> = ResponseEntity.ok("Auth manager up and running.")
}