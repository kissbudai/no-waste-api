package edu.ubb.micro.nowaste.authmanager.exception

class ApiException : RuntimeException {
	constructor() : super()
	constructor(message: String) : super(message)
	constructor(message: String, throwable: Throwable) : super(message, throwable)

	companion object {
		private const val serialVersionUID = -1L
	}
}