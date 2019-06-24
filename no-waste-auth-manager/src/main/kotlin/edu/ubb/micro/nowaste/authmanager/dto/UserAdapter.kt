package edu.ubb.micro.nowaste.authmanager.dto

import edu.ubb.micro.nowaste.authmanager.controller.auth.SignUpRequestBody
import edu.ubb.micro.nowaste.authmanager.model.User

/**
 * Extension function which converts a [User] into a [UserDTO].
 *
 * @return mapped [UserDTO]
 */
fun User.toDTO(): UserDTO = UserDTO(
	id = id,
	userName = userName,
	firstName = firstName,
	lastName = lastName,
	email = email
)

/**
 * Extension function which converts a [SignUpRequestBody] into an [User].
 *
 * @return mapped [User]
 */
fun SignUpRequestBody.toUser(): User = User(
	userName = userName,
	email = email,
	firstName = firstName,
	lastName = lastName
)