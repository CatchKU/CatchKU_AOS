package com.example.catchku.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserObtainItemDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("itemName")
    val itemName: String,
)