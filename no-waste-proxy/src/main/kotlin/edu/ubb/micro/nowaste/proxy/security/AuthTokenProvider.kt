package edu.ubb.micro.nowaste.proxy.security

import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest

interface AuthTokenProvider {

    /**
     * Determines the [Authentication] for the provided token.
     *
     * @param token JWT Token which contains the Authentication information.
     */
    @Throws(AuthenticationException::class)
    fun getAuthentication(token: String): Authentication

    /**
     * Determines the token from [request] header if passed.
     *
     * @return Authentication token if can be found, null otherwise.
     */
    fun resolveToken(request: HttpServletRequest): String?

    /**
     * Validates an Authentication Token.
     *
     * @param token Token required to be validated.
     *
     * @return The state of the token. True if the token is valid, false otherwise.
     */
    fun validateToken(token: String): Boolean

    /**
     * Decrypts the user id from the token
     *
     * @param token Valid Token which contains a user id
     */
    fun getUserIdFromToken(token: String): String

}