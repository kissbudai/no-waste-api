package edu.ubb.micro.nowaste.authmanager.service

import edu.ubb.micro.nowaste.authmanager.model.User
import javax.servlet.http.HttpServletRequest

interface UserAuthUseCase {

	/**
	 * Determines the session user object from Authorization.
	 *
	 * @return [User] or null if couldn't be found based on authorization.
	 */
	@Throws(ServiceException::class)
	fun getSessionUser(request: HttpServletRequest): User
}