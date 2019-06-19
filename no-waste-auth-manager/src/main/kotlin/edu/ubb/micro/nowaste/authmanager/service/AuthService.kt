package edu.ubb.micro.nowaste.authmanager.service

import edu.ubb.micro.nowaste.authmanager.model.User
import javax.servlet.http.HttpServletRequest

interface AuthService {

	fun getTokenForUserId(id: String): String

	/**
	 * Returns the user id which is encoded in the Authorization header
	 */
	@Throws(ServiceException::class)
	fun whoAmI(request: HttpServletRequest): String

	@Throws(ServiceException::class)
	fun authenticate(userName: String, password: String): User

	@Throws(ServiceException::class)
	fun register(user: User): User
}