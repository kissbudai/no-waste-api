package edu.ubb.micro.nowaste.authmanager.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents a User with basic fields.
 */
data class UserDTO(
	@JsonProperty("id") val id: String,
	@JsonProperty("username") val userName: String,
	@JsonProperty("first_name") val firstName: String,
	@JsonProperty("last_name") val lastName: String,
	@JsonProperty("email") val email: String
)