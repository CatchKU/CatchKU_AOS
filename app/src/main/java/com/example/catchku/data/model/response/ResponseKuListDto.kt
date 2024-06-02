package com.example.catchku.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KuListInfo(
    @SerialName("kuName")
    val kuName: String,
    @SerialName("catchedDate")
    val catchedDate: String
)

@Serializable
data class ResponseKuListDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<KuListInfo>
)