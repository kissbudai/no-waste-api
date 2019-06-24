package edu.ubb.micro.nowaste.requestmanager.controller

import edu.ubb.micro.nowaste.requestmanager.dto.RequestDTO
import edu.ubb.micro.nowaste.requestmanager.exception.ApiException
import edu.ubb.micro.nowaste.requestmanager.security.AuthorizationService
import edu.ubb.micro.nowaste.requestmanager.service.ServiceException
import edu.ubb.micro.nowaste.requestmanager.service.request.RequestService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class RequestsController(private val requestService: RequestService, private val authorizationService: AuthorizationService) {

	private val logger = LoggerFactory.getLogger(RequestsController::class.java)

	@GetMapping("/requests")
	fun getRequests(request: HttpServletRequest, @RequestParam("per_page") perPage: Int?, @RequestParam("page") page: Int? = 0): ResponseEntity<List<RequestDTO>> {

		logger.info("/requests endpoint called with perPage: $perPage and page: $page")

		val sessionUserId = authorizationService.getUserId() // getUserIdFromRequest(request)

		try {
			val requests = requestService.getRequests(sessionUserId, page ?: 1, perPage ?: DEFAULT_PER_PAGE_LIMIT)
			logger.info("/requests endpoint processed, response will be returned.")
			return ResponseEntity.ok(requests)
		} catch (ex: ServiceException) {
			logger.error("Exception during /requests endpoint call", ex)
			throw ApiException("Unable to fetch the requests", ex)
		}
	}

	@GetMapping("/requests/{id}")
	fun getById(@PathVariable("id") requestId: String): ResponseEntity<RequestDTO> {

		logger.info("/requests/:id endpoint called with id: $requestId")

		try {
			val request = requestService.getRequestById(requestId)

			return ResponseEntity.ok(request)
		} catch (ex: ServiceException) {
			logger.error("Exception during /requests/:$requestId endpoint, because: ${ex.message}", ex)
			throw ApiException("Unable to get request with $requestId", ex)
		}
	}

	@PostMapping("/requests")
	fun createRequest(@RequestParam itemId: String): ResponseEntity<RequestDTO> {

		logger.info("POST /requests endpoint called with productId $itemId")

		try {

			val createdRequest = requestService.createRequestForProduct(authorizationService.getUserId(), itemId)

			return ResponseEntity.ok(createdRequest)
		} catch (ex: ServiceException) {
			logger.error("Exception during POST /requests with productId $itemId, because: ${ex.message}", ex)
			throw ApiException("Unable to create request for item $itemId", ex)
		}
	}

	@PostMapping("/requests/{id}/confirm")
	fun confirmRequest(@PathVariable("id") requestId: String): ResponseEntity<String> =
		ResponseEntity.ok("Unfortunately, a request cannot be confirmed. Work in progress")
//		ResponseEntity.ok("POST /requests/{id}/confirm called with request $requestId. Can be called only by the item's author and will close the item and request as well.")

	@PutMapping("/requests/{id}")
	fun updateRequest(@PathVariable("id") requestId: String, @RequestParam("to_cancel") toCancel: Boolean): ResponseEntity<String> =
		ResponseEntity.ok("Unfortunately, the cancellation is not supported at the moment.")
	// ResponseEntity.ok("PUT /requests/:id called for request $requestId with to_cancel: $toCancel parameter. If the request is not finalized, will be cancelled, and the object will be returned.")

	companion object {
		private const val DEFAULT_PER_PAGE_LIMIT = 25
	}
}