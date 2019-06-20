package edu.ubb.micro.nowaste.productmanager.controller

import edu.ubb.micro.nowaste.productmanager.AUTH_SERVICE_URL
import edu.ubb.micro.nowaste.productmanager.adapter.toDTO
import edu.ubb.micro.nowaste.productmanager.adapter.toDTOList
import edu.ubb.micro.nowaste.productmanager.adapter.toModel
import edu.ubb.micro.nowaste.productmanager.dto.ProductDTO
import edu.ubb.micro.nowaste.productmanager.dto.UserDTO
import edu.ubb.micro.nowaste.productmanager.exception.ApiException
import edu.ubb.micro.nowaste.productmanager.model.Product
import edu.ubb.micro.nowaste.productmanager.service.ServiceException
import edu.ubb.micro.nowaste.productmanager.service.product.ProductService
import org.slf4j.LoggerFactory
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import javax.servlet.http.HttpServletRequest

@RestController
class ProductsController(private val productService: ProductService, private val restTemplate: RestTemplate) {

	private val logger = LoggerFactory.getLogger(ProductsController::class.java)

	@GetMapping("/products")
	fun getProductsWithPaging(@RequestParam("page") page: Int?, @RequestParam("per_page") perPage: Int?): ResponseEntity<List<ProductDTO>> {
		logger.info("Processing /products request with page $page, per_page $perPage")

		try {
			val products = getProductsWithPagination(page = page, perPage = perPage)

			logger.info("Response will be returned from /products endpoint. Response: $products")

			return ResponseEntity.ok(products.toDTOList())
		} catch (ex: ServiceException) {
			logger.error("Something went wrong while loading the product list.", ex)
			throw ApiException("Unable to fetch products", ex)
		}
	}

	@GetMapping("/products/{id}")
	fun getProductById(@PathVariable("id") productId: String): ResponseEntity<ProductDTO> {
		logger.info("Product with id $productId requested.")

		try {
			val product = productService.getProductById(productId) ?: throw ResourceNotFoundException("Product not found")

			return ResponseEntity.ok(product.toDTO())
		} catch (ex: ServiceException) {
			logger.error("Something went wrong while processing the /product/$productId request", ex)
			throw ApiException("Unable to return product for $productId", ex)
		}
	}

	@PostMapping("/products")
	fun createNewProduct(request: HttpServletRequest, @RequestBody productCreationBody: ProductCreationRequestBody): ResponseEntity<ProductDTO> {

		logger.info("PUT /products called")

		try {

			val headers = HttpHeaders().apply {
				contentType = MediaType.APPLICATION_JSON
				set("Authorization", request.getHeader("Authorization"))
			}

			val httpEntity = HttpEntity("parameters", headers)

			//restTemplate.getForEntity("http://auth-manager-service/user/me", User::class.java).body
			val creatorUser = restTemplate.exchange("$AUTH_SERVICE_URL/user/me", HttpMethod.GET, httpEntity, UserDTO::class.java).body
				?: throw ApiException("Unable to attach this request to a user")

			val product = productService.create(productCreationBody, creatorUser.toModel())

			return ResponseEntity.ok(product.toDTO())
		} catch (ex: ServiceException) {
			logger.error("Error during PUT /products", ex)
			throw ApiException("Couldn't create product", ex)
		}
	}

	@PutMapping("/products/{id}/status")
	fun updateProductStatus(request: HttpServletRequest, @PathVariable("id") productId: String, @RequestParam("new_status") newStatus: Int): ResponseEntity<ProductDTO> {
		val status = Product.fromValueToStatus(newStatus)

		// TODO:
		// verify if the item is not finalized. If the product is in open state, can be changed only to PENDING, if it's pending, can be changed only to OPEN.
		try {
			productService.updateStatus(productId, status)
			val product = productService.getProductById(productId) ?: throw ResourceNotFoundException("Couldn't find product with id $productId")

			return ResponseEntity.ok(product.toDTO())
		} catch (ex: ServiceException) {
			logger.error("Unable to update status for product with id $productId, because ${ex.message}", ex)
			throw ApiException("Couldn't update product status", ex)
		}
	}

	@Throws(ServiceException::class)
	private fun getProductsWithPagination(page: Int?, perPage: Int?): List<Product> {

		return if (page == null && perPage == null) {
			productService.getProducts()
		} else {

			if (page == null || perPage == null) {
				throw ApiException("Provide both page and per_page parameters or none of them")
			} else {
				productService.getProducts(perPage = perPage, page = page)
			}
		}
	}
}