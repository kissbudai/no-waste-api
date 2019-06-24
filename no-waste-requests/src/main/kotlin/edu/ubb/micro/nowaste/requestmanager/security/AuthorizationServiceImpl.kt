package edu.ubb.micro.nowaste.requestmanager.security

import edu.ubb.micro.nowaste.requestmanager.exception.NoAuthorizationHeader
import edu.ubb.micro.nowaste.requestmanager.exception.NotAllowedException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest

@Service
class AuthorizationServiceImpl : AuthorizationService {

	override fun resolveToken(request: HttpServletRequest): String? {
		val bearerToken = request.getHeader(JwtConfig.TOKEN_HEADER_STRING)

		return if (bearerToken != null && bearerToken.startsWith(JwtConfig.BEARER_PREFIX)) {
			bearerToken.substring(JwtConfig.BEARER_PREFIX.length + 1)
		} else {
			null
		}
	}

	override fun getUserIdFromRequest(request: HttpServletRequest): String {
		val token = resolveToken(request) ?: throw NoAuthorizationHeader()

		return getUserIdFromToken(token)
	}

	override fun getAuthentication(token: String): Authentication {
		val userId = getUserIdFromToken(token)

		val userAuth = org.springframework.security.core.userdetails.User
			.withUsername(userId)
			.password(userId)
			.authorities(emptyList())
			.accountExpired(false)
			.accountLocked(false)
			.credentialsExpired(false)
			.disabled(false)
			.build()

		return UsernamePasswordAuthenticationToken(userAuth, "", userAuth.authorities)
	}

	override fun validateToken(token: String): Boolean {
		try {
			Jwts.parser().setSigningKey(JwtConfig.JWT_TOKEN_SECRET).parseClaimsJws(token)
			return true
		} catch (exception: Exception) {
			when (exception) {
				is JwtException, is IllegalArgumentException -> throw AuthenticationException()
				else -> throw exception
			}
		}
	}

	override fun getUserId(): String {
		val authentication = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
		return (authentication.principal as User).username
	}

	override fun canAccessUserResource(userId: String) {
		val currentUserId = getUserId()

		if (userId != currentUserId) {
			throw NotAllowedException("Access not allowed for resource for user with $userId")
		}
	}

	private fun getUserIdFromToken(token: String): String = Jwts.parser().setSigningKey(JwtConfig.JWT_TOKEN_SECRET).parseClaimsJws(token).body.subject
}