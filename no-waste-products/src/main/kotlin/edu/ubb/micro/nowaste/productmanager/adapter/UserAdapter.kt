package edu.ubb.micro.nowaste.productmanager.adapter

import edu.ubb.micro.nowaste.productmanager.dto.UserDTO
import edu.ubb.micro.nowaste.productmanager.model.User

fun User.toDTO() = UserDTO(
	id = id,
	firstName = firstName,
	lastName = lastName
)

fun UserDTO.toModel() = User(
	id = id,
	firstName = firstName,
	lastName = lastName
)