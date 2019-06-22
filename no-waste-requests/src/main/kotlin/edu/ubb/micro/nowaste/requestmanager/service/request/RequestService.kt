package edu.ubb.micro.nowaste.requestmanager.service.request

import edu.ubb.micro.nowaste.requestmanager.dto.RequestDTO
import edu.ubb.micro.nowaste.requestmanager.service.ServiceException

interface RequestService {

	/**
	 * Loads a list of [RequestDTO] described by [page] and [perPage].
	 *
	 * @param userId The user for who the requests has to be fetched.
	 * @param page Which page should be request (1 based indexing).
	 * @param perPage How many elements should be loaded in a single fetch.
	 *
	 * @return The fetched [RequestDTO] list.
	 */
	@Throws(ServiceException::class)
	fun getRequests(userId: String, page: Int, perPage: Int): List<RequestDTO>

	/**
	 * Loads a single [RequestDTO] determined by [requestId].
	 *
	 * @param requestId The id of the requested resource.
	 *
	 * @return The resource for the requested id or exception if the resource is not present or can't be accessed by
	 * the user.
	 */
	@Throws(ServiceException::class)
	fun getRequestById(requestId: String): RequestDTO

	/**
	 * Creates a new request for an product identified by [productId].
	 * Throws an exception if the product is not in OPEN state.
	 *
	 * @param productId The id of the product for which the request should be created.
	 *
	 * @return The created request
	 */
	@Throws(ServiceException::class)
	fun createRequestForProduct(productId: String): RequestDTO

	/**
	 * Cancels a request or throws exception if the request is not in OPEN/Pending states. If the request is cancelled,
	 * then the ProductInfo is restored to an OPEN state.
	 *
	 * @param requestId The id of the request which should be cancelled.
	 */
	@Throws(ServiceException::class)
	fun cancelRequest(requestId: String)

	/**
	 * Confirms a request as done only if the request is not in terminated state. Updates the status of the product as well
	 * to mark it as DONE.
	 *
	 * @param requestId The id of the Request which should be marked as done.
	 */
	@Throws(ServiceException::class)
	fun confirmRequest(requestId: String)
}