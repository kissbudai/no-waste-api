package edu.ubb.micro.nowaste.productmanager.controller

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents the minimum and required field in order to create a new Item.
 */
data class ProductCreationRequestBody @JsonCreator constructor(
	@JsonProperty("name") val name: String,
	@JsonProperty("p") val description: String,
	@JsonProperty("type") val type: Int,
	@JsonProperty("price") val price: Float?,
	@JsonProperty("pickup_information") val pickupInformation: String,
	@JsonProperty("validity") val validity: Long?,
	@JsonProperty("quantity") val quantity: Int?,
	@JsonProperty("category_id") val categoryId: String
)