package com.example.catchku.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestKuCatchDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("KuName")
    val kuName: String,
)