package com.example.catchku

import com.example.catchku.request.RequestItemDeleteDto
import com.example.catchku.request.RequestRegisterDto
import com.example.catchku.response.ResponseDto
import com.example.catchku.response.ResponseRegisterDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST("user/register")
    fun register(
        @Body request: RequestRegisterDto,
    ): Call<ResponseRegisterDto>

    @DELETE("item/delete")
    fun itemDelete(@Body request: RequestItemDeleteDto): Call<ResponseDto>

}
