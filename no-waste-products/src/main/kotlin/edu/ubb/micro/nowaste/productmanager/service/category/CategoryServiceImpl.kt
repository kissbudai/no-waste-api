package edu.ubb.micro.nowaste.productmanager.service.category

import edu.ubb.micro.nowaste.productmanager.model.Category
import edu.ubb.micro.nowaste.productmanager.repository.CategoryRepository
import org.hibernate.service.spi.ServiceException
import org.springframework.dao.DataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

	override fun getCategoryById(categoryId: String): Category {
		try {
			return categoryRepository.findByIdOrNull(categoryId)
				?: throw ResourceNotFoundException("Unable to find the wanted category")
		} catch (ex: DataAccessException) {
			throw ServiceException("Unable to get category with id $categoryId", ex)
		}
	}
}