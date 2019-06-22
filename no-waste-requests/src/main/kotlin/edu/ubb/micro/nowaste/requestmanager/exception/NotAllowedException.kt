package edu.ubb.micro.nowaste.requestmanager.exception

class NotAllowedException : RuntimeException {
	constructor(message: String) : super(message)
	constructor(message: String, throwable: Throwable) : super(message, throwable)

	companion object {
		private const val serialVersionUID = -1L
	}
}
