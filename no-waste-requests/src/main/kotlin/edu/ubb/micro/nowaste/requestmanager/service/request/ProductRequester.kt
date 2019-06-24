package edu.ubb.micro.nowaste.requestmanager.service.request

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import edu.ubb.micro.nowaste.requestmanager.PRODUCT_SERVICE_URL
import edu.ubb.micro.nowaste.requestmanager.exception.ApiException
import edu.ubb.micro.nowaste.requestmanager.service.ServiceException
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ProductRequester(private val restTemplate: RestTemplate) {

	@HystrixCommand(fallbackMethod = "handleProductServiceError")
	fun getProductInfoFromProductsService(productId: String): ProductStatusInfoDTO? {
		// We need to check if the offer still exists, so a call is required to the other service, after that, if the item exists there, then 100% it is OPEN.
		return restTemplate.getForEntity("$PRODUCT_SERVICE_URL/$productId", ProductStatusInfoDTO::class.java).body
	}

	fun handleProductServiceError(productId: String): ProductStatusInfoDTO? {
		throw ApiException("Unable to create request for product $productId", ServiceException("Unable to access products service"))
	}
}