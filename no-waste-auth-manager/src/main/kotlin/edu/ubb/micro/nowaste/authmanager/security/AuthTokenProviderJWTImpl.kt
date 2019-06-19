package edu.ubb.micro.nowaste.authmanager.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date
import javax.servlet.http.HttpServletRequest

/**
 * [AuthTokenProvider] implementation which uses Json Web Tokens respecting the Bearer schema.
 */
@Component
class AuthTokenProviderJWTImpl : AuthTokenProvider {

	@Suppress("DEPRECATION")
	override fun createToken(userId: String): String = Jwts.builder()
		.setSubject(userId)
		.setExpiration(Date(System.currentTimeMillis() + JwtConfig.JWT_TOKEN_EXPIRATION))
		.signWith(SignatureAlgorithm.HS256, JwtConfig.JWT_TOKEN_SECRET)
		.compact()

	override fun resolveToken(request: HttpServletRequest): String? {
		val bearerToken = request.getHeader(JwtConfig.TOKEN_HEADER_STRING)

		return if (bearerToken != null && bearerToken.startsWith(JwtConfig.BEARER_PREFIX)) {
			bearerToken.substring(JwtConfig.BEARER_PREFIX.length + 1)
		} else {
			null
		}
	}

	override fun getUserIdFromToken(token: String): String =
		Jwts.parser().setSigningKey(JwtConfig.JWT_TOKEN_SECRET).parseClaimsJws(token).body.subject
}