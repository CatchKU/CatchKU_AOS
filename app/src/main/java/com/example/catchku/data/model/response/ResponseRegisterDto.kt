package com.example.catchku.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("id") val id: Int,
    @SerialName("email") val email: String,
    @SerialName("name") val name: String
)
@Serializable
data class ResponseRegisterDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: UserInfo,
)
