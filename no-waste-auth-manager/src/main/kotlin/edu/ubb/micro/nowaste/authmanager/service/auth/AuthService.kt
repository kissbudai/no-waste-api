package edu.ubb.micro.nowaste.authmanager.service.auth

import edu.ubb.micro.nowaste.authmanager.model.User
import edu.ubb.micro.nowaste.authmanager.service.ServiceException
import javax.servlet.http.HttpServletRequest

interface AuthService {

	/**
	 * Creates a valid JWT token for the user. The token will encode the [id] of the user.
	 *
	 * @param id The id of the user who will use this token to authenticate himself.
	 *
	 * @return Generated JWT token.
	 */
	fun getTokenForUserId(id: String): String

	/**
	 * Determines the user who want to perform a given request. The user is determined from the passed token in the request header. If the token is not provided
	 * or it's not valid anymore, a [ServiceException] will be thrown.
	 *
	 * @return Id of the user or exception.if a valid token was passed in ]
	 */
	@Throws(ServiceException::class)
	fun whoAmI(request: HttpServletRequest): String

	/**
	 * Performs authentication with the provided [userName] and [password]. Tries to find a user and verify the password. If the user can't be found or the
	 * password is not correct, then an InvalidCredential exception will be thrown.
	 *
	 * @throws [ServiceException] if during the authentication, an error occurs while the data is retrieved from DB.
	 *
	 * @return User object in case of the passed credentials are valid.
	 */
	@Throws(ServiceException::class)
	fun authenticate(userName: String, password: String): User

	/**
	 * Creates a new user.
	 *
	 * @param user User object which will be persisted.
	 *
	 * @return The saved user object.
	 */
	@Throws(ServiceException::class)
	fun register(user: User): User
}