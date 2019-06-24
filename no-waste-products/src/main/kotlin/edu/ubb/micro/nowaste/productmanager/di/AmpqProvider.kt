package edu.ubb.micro.nowaste.productmanager.di

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Provides all the configurations required for CloudAMPQ Queue.
 */
@Configuration
class AmpqProvider {

	@Bean(name = [PRODUCT_AMPQ_CONNECTION_FACTORY])
	fun provideAmpqConnectionFactory(): CachingConnectionFactory = CachingConnectionFactory(AMPQ_HOST_NAME).apply {
		username = AMPQ_USERNAME
		setPassword(AMPQ_PASSWORD)
		virtualHost = AMPQ_VIRTUAL_HOST

		//Recommended settings
		setRequestedHeartBeat(30)
		setConnectionTimeout(30000)
	}

	@Bean
	fun provideRabbitAdmin(@Qualifier(PRODUCT_AMPQ_CONNECTION_FACTORY) connectionFactory: CachingConnectionFactory): RabbitAdmin =
		RabbitAdmin(connectionFactory)

	companion object {
		private const val AMPQ_HOST_NAME = "bear.rmq.cloudamqp.com"
		private const val AMPQ_USERNAME = "ujtqtclb"
		private const val AMPQ_PASSWORD = "C_sGgVG_6yx3GsT7J-A4Y2xfWxQrespa"
		private const val AMPQ_VIRTUAL_HOST = "ujtqtclb"

		const val PRODUCT_AMPQ_CONNECTION_FACTORY = "productConnectionFactory"
		const val QUEUE_REQUESTS_TO_PRODUCTS = "queueFromRequestsToProducts"
	}
}