package edu.ubb.micro.nowaste.authmanager.service.user

import edu.ubb.micro.nowaste.authmanager.model.User
import edu.ubb.micro.nowaste.authmanager.service.ServiceException

interface UserService {

	@Throws(ServiceException::class)
	fun getUserById(userId: String): User
}