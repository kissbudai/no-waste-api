package edu.ubb.micro.nowaste.authmanager.config

/**
 * Configuration values for JWT implementation
 */
object JwtConfig {

	const val JWT_TOKEN_EXPIRATION = (1000 * 60 * 60 * 24).toLong() // 1 Day
	const val JWT_TOKEN_SECRET = "LongSecretStringUsedForJWTTokenCreationRequiredByUserAuthentication"

	const val BEARER_PREFIX = "Bearer"
	const val TOKEN_HEADER_STRING = "Authorization"
}