package com.example.catchku.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopFiveUserInfo(
    @SerialName("userId")
    val userId: Int,
    @SerialName("userName")
    val userName: String,
    @SerialName("kuCount")
    val kuCount: Int
)

@Serializable
data class ResponseTopFiveUserDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<TopFiveUserInfo>
)