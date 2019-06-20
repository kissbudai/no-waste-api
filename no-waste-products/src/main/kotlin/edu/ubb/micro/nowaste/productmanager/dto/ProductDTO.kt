package edu.ubb.micro.nowaste.productmanager.dto

import com.fasterxml.jackson.annotation.JsonProperty
import edu.ubb.micro.nowaste.productmanager.model.User

data class ProductDTO(
	@JsonProperty("id") val id: String,
	@JsonProperty("name") val name: String,
	@JsonProperty("description") val description: String,
	@JsonProperty("sender") val sender: User,
	@JsonProperty("type") val type: Int,
	@JsonProperty("status") val status: Int,
	@JsonProperty("price") val price: Float,
	@JsonProperty("pickup_information") val pickupInformation: String,
	@JsonProperty("category") val category: CategoryDTO
)