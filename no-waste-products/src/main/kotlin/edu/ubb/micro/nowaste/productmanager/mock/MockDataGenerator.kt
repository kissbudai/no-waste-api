package edu.ubb.micro.nowaste.productmanager.mock

import edu.ubb.micro.nowaste.productmanager.model.Category
import edu.ubb.micro.nowaste.productmanager.model.Product
import edu.ubb.micro.nowaste.productmanager.model.User
import edu.ubb.micro.nowaste.productmanager.repository.CategoryRepository
import edu.ubb.micro.nowaste.productmanager.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.Random

/**
 * Generates mocked database content for categories and Products.
 */
@Service
class MockDataGenerator(private val productRepository: ProductRepository, private val categoryRepository: CategoryRepository) {

	private val generatedCategories = mutableListOf<Category>()

	fun generate() {
		generateCategoryList()
		createProducts()
	}

	private fun createProducts() {
		val products = (0..50).map(::randomProduct)

		productRepository.saveAll(products)
	}

	private fun randomProduct(i: Int) = Product(
		name = "Random Item $i",
		description = "Random item description $i",
		pickupInformation = "Can be picked in: $i",
		type = Product.Type.FREE,
		price = 0f,
		status = Product.Status.OPEN,
		sender = User("non-existing-user-id", "FirstN", "LastN"),
		category = generatedCategories[Random().nextInt(generatedCategories.size - 1)]
	)

	private fun generateCategoryList() {
		val categories = listOf(Category("food", "Only eatable items"), Category("non-food", "Non-eatable item"))
		generatedCategories.addAll(categories)

		categoryRepository.saveAll(categories)
	}
}