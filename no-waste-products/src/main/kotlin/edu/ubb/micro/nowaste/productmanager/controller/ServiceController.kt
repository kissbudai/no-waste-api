package edu.ubb.micro.nowaste.productmanager.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceController {

	@RequestMapping("/products/health")
	fun getServiceHealth(): ResponseEntity<String> = ResponseEntity.ok("Product manager up and running.")
}