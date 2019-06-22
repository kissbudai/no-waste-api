package edu.ubb.micro.nowaste.productmanager.adapter

import edu.ubb.micro.nowaste.productmanager.dto.UserDTO
import edu.ubb.micro.nowaste.productmanager.model.User

/**
 * Converts a [UserDTO] to [User] model.
 */
fun UserDTO.toModel() = User(
	id = id,
	firstName = firstName,
	lastName = lastName
)