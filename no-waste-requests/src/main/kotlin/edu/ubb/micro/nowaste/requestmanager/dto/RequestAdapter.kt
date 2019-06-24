package edu.ubb.micro.nowaste.requestmanager.dto

import edu.ubb.micro.nowaste.requestmanager.model.Request

/**
 * Extension function which converts an [Request] into an [RequestDTO].
 *
 * @return mapped list of [RequestDTO]
 */
fun Request.toDTO() = RequestDTO(
	id = id,
	productId = productId,
	status = status.code,
	requesterId = requesterId,
	createdAt = createdAt
)

/**
 * Extension function which converts a list of [Request]s into a list of [RequestDTO]s.
 *
 * @return mapped list of [RequestDTO]
 */
fun List<Request>.toDTOList() = this.map { it.toDTO() }
