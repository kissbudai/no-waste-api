package edu.ubb.micro.nowaste.productmanager.adapter

import edu.ubb.micro.nowaste.productmanager.model.Category
import edu.ubb.micro.nowaste.productmanager.dto.CategoryDTO

/**
 * Extension function which converts a [Category] into an [CategoryDTO].
 *
 * @return mapped [CategoryDTO]
 */
fun Category.toDTO() = CategoryDTO(
	id = id,
	name = name,
	description = description
)

/**
 * Extension function which converts an [CategoryDTO] into an [Category].
 *
 * @return mapped [Category]
 */
fun CategoryDTO.toModel() = Category(
	name = name,
	description = description
)