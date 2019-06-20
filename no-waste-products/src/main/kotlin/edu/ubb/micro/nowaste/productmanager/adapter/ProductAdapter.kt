package edu.ubb.micro.nowaste.productmanager.adapter

import edu.ubb.micro.nowaste.productmanager.model.Product
import edu.ubb.micro.nowaste.productmanager.dto.ProductDTO

/**
 * Extension function which converts an [Product] into an [ProductDTO].
 *
 * @return mapped [ProductDTO]
 */
fun Product.toDTO(): ProductDTO = ProductDTO(
	id = id,
	name = name,
	type = type.value,
	sender = sender,
	status = status.value,
	price = price,
	description = description,
	pickupInformation = pickupInformation,
	category = category.toDTO()
)

/**
 * Extension function which converts a list of [Product]s into a list of [ProductDTO]s.
 *
 * @return mapped list of [ProductDTO]
 */
fun List<Product>.toDTOList(): List<ProductDTO> = this.map { it.toDTO() }


/**
 * Extension function which converts an [ProductDTO] into an [Product].
 *
 * @return mapped [Product]
 */
fun ProductDTO.toModel(): Product = Product(
	name = name,
	type = Product.fromValueToType(type),
	sender = sender,
	status = Product.fromValueToStatus(status),
	price = price,
	description = description,
	pickupInformation = pickupInformation,
	category = category.toModel()
)