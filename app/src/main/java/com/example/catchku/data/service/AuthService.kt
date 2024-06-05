package com.example.catchku.data.service

import com.example.catchku.data.model.request.RequestUserUseItemDto
import com.example.catchku.data.model.request.RequestKuCatchDto
import com.example.catchku.data.model.request.RequestKuCreateDto
import com.example.catchku.data.model.request.RequestKuDeleteDto
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserObtainItemDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseDto
import com.example.catchku.data.model.response.ResponseKuCreateDto
import com.example.catchku.data.model.response.ResponseKuListDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseTopFiveUserDto
import com.example.catchku.data.model.response.ResponseUserItemListDto
import com.example.catchku.data.model.response.ResponseUserLoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @GET("ku/top5-department")
    suspend fun topFiveDepartment(): ResponseTopFiveDepartmentDto

    @GET("ku/top5-user")
    suspend fun topFiveUser(): ResponseTopFiveUserDto

    @GET("user/item-list")
    suspend fun getUserItemList(@Header("userId") userId: Int): ResponseUserItemListDto

    @POST("user/obtain-item")
    suspend fun userObtainItem(@Body request: RequestUserObtainItemDto): ResponseDto

    @HTTP(method = "DELETE", path = "user/use-item", hasBody = true)
    suspend fun userUseItem(
        @Body request: RequestUserUseItemDto
    ): ResponseDto

    @GET("user/ku-list")
    suspend fun getKuList(@Header("userId") userId: Int): ResponseKuListDto

    @POST("user/register")
    suspend fun register(
        @Body request: RequestUserRegisterDto,
    ): ResponseRegisterDto

    @POST("user/login")
    suspend fun login(
        @Body request: RequestUserLoginDto
    ): ResponseUserLoginDto

    @POST("user/catch-ku")
    suspend fun kuCatch(
        @Body request: RequestKuCatchDto
    )

}
