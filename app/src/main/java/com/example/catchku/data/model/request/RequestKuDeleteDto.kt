package com.example.catchku.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestKuDeleteDto(
    @SerialName("kuName")
    val kuName: String,
)