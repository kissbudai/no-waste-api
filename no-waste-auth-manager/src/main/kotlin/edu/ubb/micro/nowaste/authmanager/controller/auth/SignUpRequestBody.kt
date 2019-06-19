package edu.ubb.micro.nowaste.authmanager.controller.auth

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents a DTO for sign up.
 */
data class SignUpRequestBody @JsonCreator constructor(
    @JsonProperty("username") val userName: String,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String
)