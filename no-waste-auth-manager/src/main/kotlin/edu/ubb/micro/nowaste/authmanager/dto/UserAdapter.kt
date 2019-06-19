package edu.ubb.micro.nowaste.authmanager.dto

import edu.ubb.micro.nowaste.authmanager.controller.auth.SignUpRequestBody
import edu.ubb.micro.nowaste.authmanager.model.User

/**
 * Extension function which converts a [UserDTO] into an [User].
 *
 * @return mapped [User]
 */
fun UserDTO.toUser(): User = User(
	userName = userName,
	email = email,
	firstName = firstName,
	lastName = lastName
).apply { id = this@toUser.id }

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

fun toUserDTO(user: User) = user.toDTO()

/**
 * Extension function which converts a list of [User]s into a list of [UserDTO]s.
 *
 * @return mapped list of [UserDTO]s
 */
fun List<User>.toDTO(): List<UserDTO> = this.map { it.toDTO() }

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