package com.example.catchku.domain.repository

import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseRegisterDto

interface UserRepository {
    suspend fun postRegisterUser(requestUserRegisterDto: RequestUserRegisterDto): Result<ResponseRegisterDto>

    suspend fun postLoginUser(requestUserLoginDto: RequestUserLoginDto): Result<Unit>
}