package edu.ubb.micro.nowaste.productmanager.service.product

import edu.ubb.micro.nowaste.productmanager.controller.ProductCreationRequestBody
import edu.ubb.micro.nowaste.productmanager.model.Product
import edu.ubb.micro.nowaste.productmanager.model.User
import edu.ubb.micro.nowaste.productmanager.service.ServiceException

interface ProductService {
	/**
	 * Fetches all the [Product]s from th
	 */
	@Throws(ServiceException::class)
	fun getProducts(): List<Product>

	/**
	 * Returns a [perPage] number of [Product]s from [page]. Can return fewer products than [perPage] if the request
	 * means the last page and can't find exactly [perPage] number of elements.
	 *
	 * @param perPage The maximum required number of products.
	 * @param page The page number. 1-based page index.
	 *
	 * @return List of [Product]
	 */
	@Throws(ServiceException::class)
	fun getProducts(perPage: Int, page: Int): List<Product>

	/**
	 * Tries to find an [Product] defined by id [productId].
	 *
	 * @param productId Id of the requested [Product]
	 *
	 * @return [Product] if exists for an product with [productId] id or null.
	 */
	@Throws(ServiceException::class)
	fun getProductById(productId: String): Product?

	/**
	 * Creates an [Product] by persisting it.
	 *
	 * @param product Product data used to create an [Product].
	 * @param creator User who will be attached to the product as creator
	 *
	 * @return The created product.
	 */
	@Throws(ServiceException::class)
	fun create(product: ProductCreationRequestBody, creator: User): Product

	@Throws(ServiceException::class)
	fun updateStatus(productId: String, status: Product.Status)
}