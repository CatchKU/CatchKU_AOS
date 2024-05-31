package com.example.catchku.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserRegisterDto(
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("password")
    val password: String,
    @SerialName("departmentName")
    val departmentName: String,
)