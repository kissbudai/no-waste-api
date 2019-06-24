package edu.ubb.micro.nowaste.requestmanager.service

/**
 * [RuntimeException] which can be thrown by Service layer.
 */
class ServiceException : RuntimeException {
	constructor(message: String) : super(message)
	constructor(message: String, throwable: Throwable) : super(message, throwable)

	companion object {
		private const val serialVersionUID = 1L
	}
}