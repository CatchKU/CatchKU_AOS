package com.example.catchku.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KuInfo(
    @SerialName("name") val name: String,
    @SerialName("score") val score: Int,
)
@Serializable
data class ResponseKuCreateDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: KuInfo,
)
