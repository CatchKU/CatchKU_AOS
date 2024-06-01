package com.example.catchku.data.service

import com.example.catchku.data.model.request.RequestItemCreateDto
import com.example.catchku.data.model.request.RequestItemDeleteDto
import com.example.catchku.data.model.request.RequestKuCreateDto
import com.example.catchku.data.model.request.RequestKuDeleteDto
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseDto
import com.example.catchku.data.model.response.ResponseItemCreateDto
import com.example.catchku.data.model.response.ResponseKuCreateDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseTopFiveUserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("ku/create")
    fun kuCreate(
        @Body request: RequestKuCreateDto,
    ): Call<ResponseKuCreateDto>

    @DELETE("ku/delete")
    fun kuDelete(
        @Body request: RequestKuDeleteDto,
    ): Call<ResponseDto>

    @GET("ku/top5-department")
    suspend fun topFiveDepartment(): ResponseTopFiveDepartmentDto

    @GET("ku/top5-user")
    suspend fun topFiveUser(): ResponseTopFiveUserDto

    @POST("item/create")
    fun itemCreate(@Body request: RequestItemCreateDto): Call<ResponseItemCreateDto>

    @DELETE("item/delete")
    fun itemDelete(@Body request: RequestItemDeleteDto): Call<ResponseDto>

    @POST("user/register")
    suspend fun register(
        @Body request: RequestUserRegisterDto,
    ): ResponseRegisterDto

    @POST("user/login")
    suspend fun login(
        @Body request: RequestUserLoginDto
    )

}
