package edu.ubb.micro.nowaste.productmanager.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class UserDTO @JsonCreator constructor(
	@JsonProperty("id") val id: String,
	@JsonProperty("first_name") val firstName: String,
	@JsonProperty("last_name") val lastName: String
) : Serializable