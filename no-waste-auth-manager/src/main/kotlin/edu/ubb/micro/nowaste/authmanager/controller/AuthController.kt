package edu.ubb.micro.nowaste.authmanager.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

	private val logger = LoggerFactory.getLogger(AuthController::class.java)

	@PostMapping("/auth/login")
	fun login(): ResponseEntity<String> =
		ResponseEntity.ok("POST /auth/login called. Requires a request body and will authenticate the user.")

	@PostMapping("/auth/register")
	fun register(): ResponseEntity<String> =
		ResponseEntity.ok("POST /auth/register called. Requires a request body and will create a new user.")
}