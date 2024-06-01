package com.example.catchku.domain.repository

import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseTopFiveUserDto

interface UserRepository {
    suspend fun getTopFiveDepartment(): Result<ResponseTopFiveDepartmentDto>

    suspend fun getTopFiveUser(): Result<ResponseTopFiveUserDto>

    suspend fun postRegisterUser(requestUserRegisterDto: RequestUserRegisterDto): Result<ResponseRegisterDto>

    suspend fun postLoginUser(requestUserLoginDto: RequestUserLoginDto): Result<Unit>
}