package edu.ubb.micro.nowaste.requestmanager.service.product

import edu.ubb.micro.nowaste.requestmanager.model.ProductInfo
import edu.ubb.micro.nowaste.requestmanager.repository.ProductRepository
import edu.ubb.micro.nowaste.requestmanager.service.ServiceException
import org.springframework.dao.DataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

	override fun getById(productId: String): ProductInfo? {
		try {
			return productRepository.findByIdOrNull(productId)
		} catch (ex: DataAccessException) {
			throw ServiceException("Something went wrong while queriing the product with id $productId")
		}
	}

	override fun saveAsRequested(productId: String): ProductInfo? {
		try {
			return productRepository.save(ProductInfo(productId, ProductInfo.Status.PENDING))
		} catch (ex: DataAccessException) {
			throw ServiceException("Something went wrong during the save for product with id $productId", ex)
		}
	}
}