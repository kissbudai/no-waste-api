package edu.ubb.micro.nowaste.requestmanager.service.product

import edu.ubb.micro.nowaste.requestmanager.model.ProductInfo
import edu.ubb.micro.nowaste.requestmanager.service.ServiceException

interface ProductService {

	@Throws(ServiceException::class)
	fun getById(productId: String): ProductInfo?

	@Throws(ServiceException::class)
	fun saveAsRequested(productId: String): ProductInfo?
}