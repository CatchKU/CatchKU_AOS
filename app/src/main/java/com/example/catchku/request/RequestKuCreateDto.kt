package com.example.catchku.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestKuCreateDto(
    @SerialName("kuName")
    val kuName: String,
    @SerialName("score")
    val score: Int,
)