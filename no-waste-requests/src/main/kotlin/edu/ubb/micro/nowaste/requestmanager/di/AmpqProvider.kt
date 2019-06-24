package edu.ubb.micro.nowaste.requestmanager.di

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Provides all the configurations required for CloudAMPQ Queue.
 */
@Configuration
class AmpqProvider {

	@Bean(name = [REQUEST_PRODUCT_CONNECTION_FACTORY])
	fun provideConnectionFactory(): CachingConnectionFactory = CachingConnectionFactory(AMPQ_CONNECTION_HOST_NAME).apply {
		username = AMPQ_USERNAME
		setPassword(AMPQ_PASSWORD)
		virtualHost = "ujtqtclb"
		setRequestedHeartBeat(HEART_BEAT)
		setConnectionTimeout(CONNECTION_TIMEOUT)
	}

	@Bean
	fun provideRabbitAdmin(@Qualifier(REQUEST_PRODUCT_CONNECTION_FACTORY) connectionFactory: CachingConnectionFactory) = RabbitAdmin(connectionFactory)

	@Bean
	fun provideRabbitTemplate(@Qualifier(REQUEST_PRODUCT_CONNECTION_FACTORY) connectionFactory: CachingConnectionFactory) = RabbitTemplate(connectionFactory)

	companion object {
		private const val AMPQ_CONNECTION_HOST_NAME = "bear.rmq.cloudamqp.com"
		private const val CONNECTION_TIMEOUT = 30000
		private const val HEART_BEAT = 30

		private const val REQUEST_PRODUCT_CONNECTION_FACTORY = "reqProdConnectionFactory"

		// TODO: Move these into configuration files.
		private const val AMPQ_USERNAME = "ujtqtclb"
		private const val AMPQ_PASSWORD = "C_sGgVG_6yx3GsT7J-A4Y2xfWxQrespa"
		const val QUEUE_REQUESTS_TO_PRODUCTS = "queueFromRequestsToProducts"

	}
}