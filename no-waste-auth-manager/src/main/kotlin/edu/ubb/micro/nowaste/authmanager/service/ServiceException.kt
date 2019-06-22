package edu.ubb.micro.nowaste.authmanager.service

/**
 * [RuntimeException] representing an error on Service Layer.
 */
class ServiceException : RuntimeException {
	constructor() : super()
	constructor(message: String) : super(message)
	constructor(message: String, throwable: Throwable) : super(message, throwable)

	companion object {
		private const val serialVersionUID = 1L
	}
}