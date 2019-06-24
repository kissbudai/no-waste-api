package edu.ubb.micro.nowaste.proxy.security.jwt

import edu.ubb.micro.nowaste.proxy.config.JwtConfig
import edu.ubb.micro.nowaste.proxy.security.AuthTokenProvider
import edu.ubb.micro.nowaste.proxy.security.AuthenticationException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

/**
 * [AuthTokenProvider] implementation which uses Json Web Tokens respecting the Bearer schema.
 */
@Component
class AuthTokenProviderJWTImpl : AuthTokenProvider {

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

	override fun resolveToken(request: HttpServletRequest): String? {
		val bearerToken = request.getHeader(JwtConfig.TOKEN_HEADER_STRING)

		return if (bearerToken != null && bearerToken.startsWith(JwtConfig.BEARER_PREFIX)) {
			bearerToken.substring(JwtConfig.BEARER_PREFIX.length + 1)
		} else {
			null
		}
	}

	@Throws(AuthenticationException::class)
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

	override fun getUserIdFromToken(token: String): String =
		Jwts.parser().setSigningKey(JwtConfig.JWT_TOKEN_SECRET).parseClaimsJws(token).body.subject
}