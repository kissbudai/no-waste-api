package edu.ubb.micro.nowaste.requestmanager.security

import edu.ubb.micro.nowaste.requestmanager.exception.NotAllowedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import javax.servlet.http.HttpServletRequest

interface AuthorizationService {

	fun getAuthentication(token: String): Authentication

	@Throws(AuthenticationException::class)
	fun validateToken(token: String): Boolean

	fun resolveToken(request: HttpServletRequest): String?

	fun getUserIdFromRequest(request: HttpServletRequest): String

	fun getUserId(): String

	@Throws(NotAllowedException::class)
	fun canAccessUserResource(userId: String)
}