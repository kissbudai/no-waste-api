package edu.ubb.micro.nowaste.authmanager.exception

/**
 * Represents an error emitted by this API.
 */
data class NoWasteErrorResponse(
	val errorCode: Int? = null,
	val displayMessage: String,
	val developerMessage: String?
)