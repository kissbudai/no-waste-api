package edu.ubb.micro.nowaste.requestmanager.service.request

import edu.ubb.micro.nowaste.requestmanager.PRODUCT_SERVICE_URL
import edu.ubb.micro.nowaste.requestmanager.di.AmpqProvider.Companion.QUEUE_REQUESTS_TO_PRODUCTS
import edu.ubb.micro.nowaste.requestmanager.dto.RequestDTO
import edu.ubb.micro.nowaste.requestmanager.dto.toDTO
import edu.ubb.micro.nowaste.requestmanager.dto.toDTOList
import edu.ubb.micro.nowaste.requestmanager.exception.NotAllowedException
import edu.ubb.micro.nowaste.requestmanager.model.ProductInfo
import edu.ubb.micro.nowaste.requestmanager.model.Request
import edu.ubb.micro.nowaste.requestmanager.repository.RequestRepository
import edu.ubb.micro.nowaste.requestmanager.security.AuthorizationService
import edu.ubb.micro.nowaste.requestmanager.service.ServiceException
import edu.ubb.micro.nowaste.requestmanager.service.product.ProductService
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue

@Service
class RequestServiceImpl(
	private val requestRepository: RequestRepository,
	private val productService: ProductService,
	private val authorizationService: AuthorizationService,
	private val restTemplate: RestTemplate,
	private val rabbitAdmin: RabbitAdmin,
	private val rabbitTemplate: RabbitTemplate
) : RequestService {

	private val logger = LoggerFactory.getLogger(RequestServiceImpl::class.java)

	override fun getRequests(userId: String, page: Int, perPage: Int): List<RequestDTO> {
		try {
			return requestRepository.findAllByRequesterId(userId, PageRequest.of(page - 1, perPage)).content.toDTOList()
				.also { logger.debug("Request list determined for user with page $page and perPage $perPage. ${it.size} elements will be returned.") }
		} catch (ex: DataAccessException) {
			logger.error("Error occurred while fetching the requests, because of ${ex.message}", ex)
			throw ServiceException("Unable to fetch request list", ex)
		}
	}

	override fun getRequestById(requestId: String): RequestDTO {

		val request: Request

		try {
			request = requestRepository.findByIdOrNull(requestId)
				?: throw ResourceNotFoundException("Resource with id $requestId not found")

			if (authorizationService.getUserId() != request.requesterId) {
				throw NotAllowedException("Not your request")
			}

		} catch (ex: DataAccessException) {
			logger.error("Error occurred while loading request with id $requestId, because ${ex.message}", ex)
			throw ServiceException("Unable to fetch request with id $requestId", ex)
		}

		if (request.requesterId != authorizationService.getUserId()) {
			logger.info("A user tried to access a request of another user, which is forbidden, exception is thrown for it.")
			throw NotAllowedException("Access not allowed.")
		}

		return request.toDTO()
	}

	override fun createRequestForProduct(productId: String): RequestDTO {

		logger.trace("Request creation started")

		val product = productService.getById(productId)

		return if (product != null) {
			if (product.status != ProductInfo.Status.OPEN) {
				logger.info("Request creation: The item is not open, can't be requested at the moment")
				throw ServiceException("Item can't be requested")
			}

			logger.info("Request creation: Item determined, can start the request creation.")
			requestProduct(productId)

		} else {

			// We need to check if the offer still exists, so a call is required to the other service, after that, if the item exists there, then 100% it is OPEN.
			if (restTemplate.getForEntity("$PRODUCT_SERVICE_URL/$productId", ProductStatusInfoDTO::class.java).body?.status != 0) {
				logger.info("Request creation: The item is not open, can't be requested at the moment")
				throw ServiceException("Item can't be requested")
			}

			requestProduct(productId)
		}
	}

	private fun requestProduct(productId: String): RequestDTO {
		val request = Request(
			productId = productId,
			status = Request.Status.PENDING,
			requesterId = authorizationService.getUserId(),
			createdAt = System.currentTimeMillis()
		)

		logger.trace("Request creation: request created and requires a save for persistance")

		return submitRequest(productId, request)
	}

	@Transactional
	protected fun submitRequest(productId: String, request: Request): RequestDTO {
		// TODO: Add a queue which will initiate an update on the other service as well: product service:  updateStatus(productId, ProductInfo.Status.PENDING)
		sendMessage(productId)
		productService.saveAsRequested(productId)

		try {
			return requestRepository.save(request).toDTO()
		} catch (ex: DataAccessException) {
			logger.error("Error while creating the request and updating the item, reason: ${ex.message}", ex)
			throw ServiceException("Unable to create request for item $productId", ex)
		}
	}

	private fun sendMessage(productId: String) {
		// set up the connection
//		val connectionFactory = CachingConnectionFactory("bear.rmq.cloudamqp.com")
//		connectionFactory.username = "ujtqtclb"
//		connectionFactory.setPassword("C_sGgVG_6yx3GsT7J-A4Y2xfWxQrespa")
//		connectionFactory.virtualHost = "ujtqtclb"
//
//		//Recommended settings
//		connectionFactory.setRequestedHeartBeat(30)
//		connectionFactory.setConnectionTimeout(30000)

		//Set up queue, exchanges and bindings
		rabbitAdmin.apply {
			val queue = Queue(QUEUE_REQUESTS_TO_PRODUCTS)
			declareQueue(queue)

			val exchange = TopicExchange("fanout")
			declareExchange(exchange)
			declareBinding(BindingBuilder.bind(queue).to(exchange).with("foo.*"))
		}

		rabbitTemplate.convertAndSend("fanout", "foo.bar", productId)
	}

	override fun cancelRequest(requestId: String) {
//
//		logger.debug("Request cancellation initiated")
//
//		val request = requestRepository.findByIdOrNull(requestId)
//			?: throw ResourceNotFoundException("Request with id $requestId not found")
//
//		logger.trace("Request cancellation: request determinated, requires validation")
//
//		// Only a pending Request can be cancelled
//		if (request.status != Request.Status.PENDING) {
//			logger.info("Request cancellation: request is not in Pending state, can't be cancelled. Exception is thrown for this")
//			throw ServiceException("A finalized request can't be cancelled.")
//		}
//
//		logger.trace("Request cancellation: request determined and valid for cancellation")
//
//		val item = itemService.getItemById(request.productId)
//			?: throw ResourceNotFoundException("Unable to find an item for request with id$requestId")
//
//		try {
//			request.status = Request.Status.CANCELLED
//			itemService.updateStatus(item.id, Item.Status.OPEN)
//			requestRepository.save(request)
//		} catch (ex: DataAccessException) {
//			logger.error("Request cancellation: request not cancelled, because of ${ex.message}", ex)
//			throw ServiceException("Unable to cancel request with id $requestId", ex)
//		}
	}

	override fun confirmRequest(requestId: String) {
//
//		logger.debug("Request confirmation initiated")
//
//		val request = requestRepository.findByIdOrNull(requestId)
//			?: throw ResourceNotFoundException("Request with id $requestId not found")
//
//		logger.trace("Request confirmation: request determined")
//
//		val item = itemService.getItemById(request.productId)
//			?: throw ResourceNotFoundException("Item not found for resource with id $requestId")
//
//		logger.trace("Request confirmation: item deterined for request")
//
//		if (authorizationService.getUserId() != item.sender.id) {
//			logger.info("The current user is not the author of this item and due to this, can't confirm it. Exception is thrown for it")
//			throw NotAllowedException("A request can be confirmed only by the author of the item.")
//		}
//
//		logger.trace("Request confirmation: item, request determined and the user can confirm it")
//
//		try {
//			confirmItemRequest(item.id, request)
//			logger.debug("Request confirmation: request confirmed, returning from service")
//		} catch (ex: DataAccessException) {
//			logger.error("Couldn't confirm request with id $requestId because ${ex.message}", ex)
//			throw ServiceException("Unable to update request", ex)
//		}
	}

	@Transactional
	@Throws(DataAccessException::class, ServiceException::class)
	protected fun confirmItemRequest(itemId: String, request: Request) {
//
//		itemService.updateStatus(productId, Item.Status.COMPLETED)
//		requestRepository.save(request.apply { status = Request.Status.COMPLETED })
	}

}