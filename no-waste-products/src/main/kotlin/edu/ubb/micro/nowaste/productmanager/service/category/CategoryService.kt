package edu.ubb.micro.nowaste.productmanager.service.category

import edu.ubb.micro.nowaste.productmanager.model.Category
import org.hibernate.service.spi.ServiceException

interface CategoryService {

	@Throws(ServiceException::class)
	fun getCategoryById(categoryId: String): Category
}