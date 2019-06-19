package edu.ubb.micro.nowaste.authmanager.security

import javax.servlet.http.HttpServletRequest

interface AuthTokenProvider {

	/**
	 * Creates an authentication token which encodes the user id.
	 *
	 * @param userId The user id, which will be encoded into the token.
	 *
	 * @return Authentication Token
	 */
	fun createToken(userId: String): String

	/**
	 * Determines the token from [request] header if passed.
	 *
	 * @return Authentication token if can be found, null otherwise.
	 */
	fun resolveToken(request: HttpServletRequest): String?


	/**
	 * Decrypts the user id from the token
	 *
	 * @param token Valid Token which contains a user id
	 */
	fun getUserIdFromToken(token: String): String

}