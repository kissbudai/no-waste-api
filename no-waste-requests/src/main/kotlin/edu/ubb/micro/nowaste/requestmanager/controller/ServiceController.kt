package edu.ubb.micro.nowaste.requestmanager.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceController {

	@RequestMapping("/requests/health")
	fun getServiceHealth(): ResponseEntity<String> = ResponseEntity.ok("Request manager up and running.")
}