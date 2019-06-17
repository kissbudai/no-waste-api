package edu.ubb.micro.nowaste.test

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BaseController {

	@RequestMapping("/test/health")
	fun getHealthStatus(): ResponseEntity<String> =
		ResponseEntity.ok("Test service up and running")

	@RequestMapping("/test/ok")
	fun checkStatus(): ResponseEntity<String> = ResponseEntity.ok("Nem hiszem le hogy vegre megy :O.")
}