package edu.ubb.micro.nowaste.requestmanager.service.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents a minimal response from the product-manager service about the state of a product.
 */
data class ProductStatusInfoDTO @JsonCreator constructor(@JsonProperty("id") val id: String, @JsonProperty("status") val status: Int)