package com.example.catchku.data.source

import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.service.AuthService
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun getKUTopFiveDepartment() =
        authService.topFiveDepartment()

    suspend fun getTopFiveUser() =
        authService.topFiveUser()

    suspend fun postRegisterUser(requestUserRegisterDto: RequestUserRegisterDto) =
        authService.register(requestUserRegisterDto)

    suspend fun postLoginUser(requestUserLoginDto: RequestUserLoginDto) =
        authService.login(requestUserLoginDto)
}