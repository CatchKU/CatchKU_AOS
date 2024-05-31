package com.example.catchku.data.source

import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.service.AuthService
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun postRegisterUser(requestUserRegisterDto: RequestUserRegisterDto) =
        authService.register(requestUserRegisterDto)
}