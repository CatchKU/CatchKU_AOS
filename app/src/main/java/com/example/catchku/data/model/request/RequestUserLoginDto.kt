package com.example.catchku.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserLoginDto(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
)