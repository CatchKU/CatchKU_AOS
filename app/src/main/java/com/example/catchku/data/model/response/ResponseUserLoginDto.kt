package com.example.catchku.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("departmentName")
    val departmentName: String
)

@Serializable
data class ResponseUserLoginDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: UserLogin
)