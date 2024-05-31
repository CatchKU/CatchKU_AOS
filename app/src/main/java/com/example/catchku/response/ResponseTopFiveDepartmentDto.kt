package com.example.catchku.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DepartmentInfo(
    @SerialName("departmentName")
    val departmentName: String,
    @SerialName("kuCount")
    val kuCount: Int
)

@Serializable
data class ResponseTopFiveDepartmentDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<DepartmentInfo>
)