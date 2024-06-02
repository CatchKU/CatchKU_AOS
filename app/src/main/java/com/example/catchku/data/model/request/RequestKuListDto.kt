package com.example.catchku.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestKuListDto(
    @SerialName("userId")
    val userId: Int,
)