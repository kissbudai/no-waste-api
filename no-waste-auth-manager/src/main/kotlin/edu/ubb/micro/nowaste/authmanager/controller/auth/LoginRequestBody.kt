package edu.ubb.micro.nowaste.authmanager.controller.auth

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents a DTO for login.
 */
data class LoginRequestBody @JsonCreator constructor(
    @JsonProperty("username") val userName: String,
    @JsonProperty("password") val password: String
)