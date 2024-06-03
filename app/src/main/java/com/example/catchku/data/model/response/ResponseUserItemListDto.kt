package com.example.catchku.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemInfo(
    @SerialName("itemName") val itemName: String,
    @SerialName("count") val count: Int,
)
@Serializable
data class ResponseUserItemListDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<ItemInfo>,
)
