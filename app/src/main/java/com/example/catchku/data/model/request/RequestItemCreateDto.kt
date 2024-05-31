package com.example.catchku.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestItemCreateDto(
    @SerialName("itemName")
    val itemName: String,
    @SerialName("itemValue")
    val itemValue: Int,
)