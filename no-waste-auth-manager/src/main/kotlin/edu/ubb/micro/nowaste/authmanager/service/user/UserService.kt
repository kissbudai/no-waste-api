package edu.ubb.micro.nowaste.authmanager.service.user

import edu.ubb.micro.nowaste.authmanager.model.User
import edu.ubb.micro.nowaste.authmanager.service.ServiceException

interface UserService {

	/**
	 * Retrieves from source the [User] identified by [userId] id.
	 *
	 * @return User who's identifier is [userId].
	 */
	@Throws(ServiceException::class)
	fun getUserById(userId: String): User
}