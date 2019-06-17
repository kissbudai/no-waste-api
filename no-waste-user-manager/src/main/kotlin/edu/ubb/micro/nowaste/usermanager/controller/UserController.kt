package edu.ubb.micro.nowaste.usermanager.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class UserController {

	private val logger = LoggerFactory.getLogger(UserController::class.java)

	@GetMapping("/users")
	fun getUsers(@RequestParam("per_page") perPage: Int?, @RequestParam("page") page: Int? = 0): ResponseEntity<String> =
		ResponseEntity.ok("GET /users called with per_page: $perPage and page: $page params. User list will be returned.")

	@GetMapping("/users/{id}")
	fun getById(@PathVariable("id") userId: String): ResponseEntity<String> =
		ResponseEntity.ok("GET /users/:id called with id $userId. User with id $userId will be returned.")

	@GetMapping("/users/me")
	fun getMe(request: HttpServletRequest): ResponseEntity<String> =
		ResponseEntity.ok("GET /users/me called. Session user will be returned.")

	@PutMapping("/users/me")
	fun updateMe(request: HttpServletRequest): ResponseEntity<String> =
		ResponseEntity.ok("PUT /users/me called. Request an update request body and will update the session user object.")
}