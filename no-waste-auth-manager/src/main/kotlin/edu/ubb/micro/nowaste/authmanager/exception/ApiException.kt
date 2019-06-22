package edu.ubb.micro.nowaste.authmanager.exception

/**
 * [RuntimeException] representing the top level exception. It can be thrown by the Controller Layer and this exception will be mapped before the response is
 * emitted by Spring in [RestExceptionHandler].
 */
class ApiException : RuntimeException {
	constructor() : super()
	constructor(message: String) : super(message)
	constructor(message: String, throwable: Throwable) : super(message, throwable)

	companion object {
		private const val serialVersionUID = -1L
	}
}