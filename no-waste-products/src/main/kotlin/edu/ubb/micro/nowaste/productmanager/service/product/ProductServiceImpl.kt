package edu.ubb.micro.nowaste.productmanager.service.product

import edu.ubb.micro.nowaste.productmanager.controller.ProductCreationRequestBody
import edu.ubb.micro.nowaste.productmanager.model.Product
import edu.ubb.micro.nowaste.productmanager.model.User
import edu.ubb.micro.nowaste.productmanager.repository.ProductRepository
import edu.ubb.micro.nowaste.productmanager.service.ServiceException
import edu.ubb.micro.nowaste.productmanager.service.category.CategoryService
import org.springframework.dao.DataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepository: ProductRepository, private val categoryService: CategoryService) :
	ProductService {

	override fun getProducts(): List<Product> {
		try {
			return productRepository.findAllByStatus(Product.Status.OPEN).toList()
		} catch (ex: Exception) {
			throw ServiceException("Error during fetching products", ex)
		}
	}

	override fun getProducts(perPage: Int, page: Int): List<Product> {
		try {
			return productRepository.findAllByStatus(Product.Status.OPEN, PageRequest.of(page - 1, perPage)).content
		} catch (ex: Exception) {
			throw ServiceException("Error during fetching $perPage products on page $page", ex)
		}
	}

	override fun getProductById(productId: String): Product? {
		try {
			return productRepository.findByIdOrNull(productId)
		} catch (ex: IllegalStateException) {
			throw ServiceException("Unable to determine product with id $productId", ex)
		}
	}

	override fun create(product: ProductCreationRequestBody, creator: User): Product {
		try {
			val productModel = Product(
				name = product.name,
				type = Product.fromValueToType(product.type),
				sender = creator,
				status = Product.Status.OPEN,
				price = product.price ?: 0f,
				description = product.description,
				pickupInformation = product.pickupInformation,
				category = categoryService.getCategoryById(product.categoryId)
			)
			return productRepository.save(productModel)
		} catch(ex: DataAccessException) {
			throw (ServiceException("Unable to create product: $product", ex))
		}
	}

	override fun updateStatus(productId: String, status: Product.Status) {
		try {
			val product = productRepository.findByIdOrNull(productId)
				?: throw ResourceNotFoundException("Item with id $productId not found")

			product.status = status

			productRepository.save(product)
		} catch (ex: DataAccessException) {
			throw ServiceException("Couldn't update the product with id $productId", ex)
		}
	}
}


