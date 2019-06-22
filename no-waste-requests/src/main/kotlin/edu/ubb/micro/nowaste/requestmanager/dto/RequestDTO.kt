package edu.ubb.micro.nowaste.requestmanager.dto

data class RequestDTO(
	val id: String,
	val productId: String,
	val status: Int,
	val requesterId: String,
	val createdAt: Long?
)