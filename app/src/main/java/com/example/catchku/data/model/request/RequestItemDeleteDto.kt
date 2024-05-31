package com.example.catchku.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestItemDeleteDto(
    @SerialName("itemName")
    val itemName: String,
)