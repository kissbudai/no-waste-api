package edu.ubb.micro.nowaste.requestmanager.dto

/**
 * Represents a Request with exposable fields to the outside world.
 */
data class RequestDTO(
	val id: String,
	val productId: String,
	val status: Int,
	val requesterId: String,
	val createdAt: Long?
)