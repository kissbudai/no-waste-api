package edu.ubb.micro.nowaste.productmanager.service.category

import edu.ubb.micro.nowaste.productmanager.model.Category
import org.hibernate.service.spi.ServiceException

interface CategoryService {

	/**
	 * Returns a category defined by id [categoryId] or throws a Resource not found exception if a non-existing category is requested.
	 *
	 * @throws ServiceException whenever something goes wrong during the data loading.
	 *
	 * @return Requested [Category] for [categoryId]
	 */
	@Throws(ServiceException::class)
	fun getCategoryById(categoryId: String): Category
}