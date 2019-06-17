package edu.ubb.micro.nowaste.requestmanager.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RequestsController {

	private val logger = LoggerFactory.getLogger(RequestsController::class.java)

	@GetMapping("/requests")
	fun getRequests(@RequestParam("per_page") perPage: Int?, @RequestParam("page") page: Int? = 0): ResponseEntity<String> =
		ResponseEntity.ok("GET /requests endpoint called with per_page: $perPage, page: $page params. List of user's requests will be returned.")

	@GetMapping("/requests/{id}")
	fun getById(@PathVariable("id") requestId: String): ResponseEntity<String> =
		ResponseEntity.ok("GET /requests/:id called with id $requestId. The item will be returned.")

	@PostMapping("/requests")
	fun createRequest(@RequestParam itemId: String): ResponseEntity<String> =
		ResponseEntity.ok("POST /requests called with item id $itemId. The created request for item with id $itemId will be returned")

	@PostMapping("/requests/{id}/confirm")
	fun confirmRequest(@PathVariable("id") requestId: String): ResponseEntity<String> =
		ResponseEntity.ok("POST /requests/{id}/confirm called with request $requestId. Can be called only by the item's author and will close the item and request as well.")

	@PutMapping("/requests/{id}")
	fun updateRequest(@PathVariable("id") requestId: String, @RequestParam("to_cancel") toCancel: Boolean): ResponseEntity<String> =
		ResponseEntity.ok("PUT /requests/:id called for request $requestId with to_cancel: $toCancel parameter. If the request is not finalized, will be cancelled, and the object will be returned.")
}