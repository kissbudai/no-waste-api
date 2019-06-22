package edu.ubb.micro.nowaste.requestmanager.di

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestProvider {

	@Bean
	@LoadBalanced
	fun provideRestTemplate(): RestTemplate = RestTemplate()
}