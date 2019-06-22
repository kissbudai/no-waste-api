package edu.ubb.micro.nowaste.requestmanager.service.product

import edu.ubb.micro.nowaste.requestmanager.model.ProductInfo
import edu.ubb.micro.nowaste.requestmanager.service.ServiceException

interface ProductService {

	/**
	 * Returns a [ProductInfo] from the source if exists, null otherwise.
	 *
	 * @param productId The id of the requested [ProductInfo].
	 */
	@Throws(ServiceException::class)
	fun getById(productId: String): ProductInfo?

	/**
	 * Saves a Product with marking as requested.
	 *
	 * @param productId The id of the requested Product.
	 *
	 * @return The save ProductInfo.
	 */
	@Throws(ServiceException::class)
	fun saveAsRequested(productId: String): ProductInfo?
}