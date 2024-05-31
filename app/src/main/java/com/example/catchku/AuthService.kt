package com.example.catchku

import com.example.catchku.request.RequestItemCreateDto
import com.example.catchku.request.RequestItemDeleteDto
import com.example.catchku.request.RequestKuCreateDto
import com.example.catchku.request.RequestKuDeleteDto
import com.example.catchku.request.RequestRegisterDto
import com.example.catchku.response.ResponseDto
import com.example.catchku.response.ResponseItemCreateDto
import com.example.catchku.response.ResponseKuCreateDto
import com.example.catchku.response.ResponseRegisterDto
import com.example.catchku.response.ResponseTopFiveDepartmentDto
import com.example.catchku.response.ResponseTopFiveUserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
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
    fun topFiveDepartment(): Call<ResponseTopFiveDepartmentDto>

    @GET("ku/top5-user")
    fun topFiveUser(): Call<ResponseTopFiveUserDto>

    @POST("item/create")
    fun itemCreate(@Body request: RequestItemCreateDto): Call<ResponseItemCreateDto>

    @DELETE("item/delete")
    fun itemDelete(@Body request: RequestItemDeleteDto): Call<ResponseDto>

    @POST("user/register")
    fun register(
        @Body request: RequestRegisterDto,
    ): Call<ResponseRegisterDto>

}
