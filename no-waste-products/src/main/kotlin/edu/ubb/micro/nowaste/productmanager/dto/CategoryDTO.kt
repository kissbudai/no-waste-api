package edu.ubb.micro.nowaste.productmanager.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryDTO(
	@JsonProperty("id") val id: String,
	@JsonProperty("name") val name: String,
	@JsonProperty("description") val description: String?
)