package edu.ubb.micro.nowaste.authmanager.exception

/**
 * [RuntimeException] representing a failed authentication. Is thrown when the authentication can be performed due to invalid credentials.
 */
class InvalidCredentialsException : RuntimeException {
	constructor(message: String) : super(message)
	constructor(message: String, throwable: Throwable) : super(message, throwable)

	companion object {
		private const val serialVersionUID = -1L
	}
}
