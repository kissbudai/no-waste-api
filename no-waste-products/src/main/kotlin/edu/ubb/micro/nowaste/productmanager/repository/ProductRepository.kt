package edu.ubb.micro.nowaste.productmanager.repository

import edu.ubb.micro.nowaste.productmanager.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

/**
 * Repository for [Product] model.
 */
@Repository
interface ProductRepository : PagingAndSortingRepository<Product, String> {

	/**
	 * Retrieves every element from source, where [Product.status] == [productStatus].
	 *
	 * @param productStatus The requested [Product.Status].
	 *
	 * @return List of [Product]s.
	 */
	fun findAllByStatus(productStatus: Product.Status): List<Product>

	/**
	 * Retrieves every element from source, where [Product.status] == [productStatus] with pagination. See [findAllByStatus]
	 * for retrieving elements without pagination.
	 *
	 * @param productStatus The requested [Product.Status]
	 * @param pageable Page information to determine the requested page.
	 *
	 * @return List of [Product]s.
	 */
	fun findAllByStatus(productStatus: Product.Status, pageable: Pageable): Page<Product>
}