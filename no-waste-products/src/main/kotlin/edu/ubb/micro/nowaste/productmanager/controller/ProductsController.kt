package edu.ubb.micro.nowaste.productmanager.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsController {

	private val logger = LoggerFactory.getLogger(ProductsController::class.java)

	@GetMapping("/products")
	fun getItemsWithPaging(@RequestParam("page") page: Int?, @RequestParam("per_page") perPage: Int?): ResponseEntity<String> =
		ResponseEntity.ok("GET /items called with page: $page and per_page $perPage parameters. The product list will be returned")

	@GetMapping("/products/{id}")
	fun getItemById(@PathVariable("id") productId: String): ResponseEntity<String> =
		ResponseEntity.ok("GET /products/:id with id $productId called. Product with id $productId will be returned.")

	@PostMapping("/products")
	fun createNewItem(): ResponseEntity<String> =
		ResponseEntity.ok("POST /products called. Requires a requestbody and will create a product item.")
}