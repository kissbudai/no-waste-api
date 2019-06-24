package edu.ubb.micro.nowaste.productmanager.service.ampq

import edu.ubb.micro.nowaste.productmanager.di.AmpqProvider
import edu.ubb.micro.nowaste.productmanager.model.Product
import edu.ubb.micro.nowaste.productmanager.service.product.ProductService
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * [AmpqReceiverService] implementation which subscribes to the message queue and listenes to update events. When an update message arrives, it means that
 * a given item (the id of the product comes in the message) was requested on the requests-microservice and this service will take care of updating the status
 * of the given product to mark as requested.
 */
@Service
class AmpqReceiverServiceImpl(
	private val productService: ProductService,
	@Qualifier(AmpqProvider.PRODUCT_AMPQ_CONNECTION_FACTORY) private val connectionFactory: CachingConnectionFactory
): AmpqReceiverService {

	private val logger = LoggerFactory.getLogger(AmpqReceiverService::class.java)

	init {
		// AMPQ setup for listening.
		val admin = RabbitAdmin(connectionFactory)
		val queue = Queue(AmpqProvider.QUEUE_REQUESTS_TO_PRODUCTS)
		admin.declareQueue(queue)
		val exchange = TopicExchange("fanout")
		admin.declareExchange(exchange)
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("foo.*"))

		listenToRequestUpdates()
	}

	private fun listenToRequestUpdates() {
		//Set up the listener
		val container = SimpleMessageListenerContainer(connectionFactory)
		val listener = object : Any() {
			fun handleMessage(productIdToUpdate: String) {

				logger.info("Message arrived that product with id $productIdToUpdate was requested. Update will happen in DB.")
				productService.updateStatus(productIdToUpdate, Product.Status.PENDING)
			}
		}

		//Send a message
		val adapter = MessageListenerAdapter(listener)
		container.setMessageListener(adapter)
		container.setQueueNames(AmpqProvider.QUEUE_REQUESTS_TO_PRODUCTS)
		container.start()
	}
}