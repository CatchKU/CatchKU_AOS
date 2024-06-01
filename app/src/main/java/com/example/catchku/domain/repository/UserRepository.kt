package com.example.catchku.domain.repository

import com.example.catchku.data.model.request.RequestKuCatchDto
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto

interface UserRepository {
    suspend fun getTopFiveDepartment(): Result<ResponseTopFiveDepartmentDto>

    suspend fun postRegisterUser(requestUserRegisterDto: RequestUserRegisterDto): Result<ResponseRegisterDto>

    suspend fun postLoginUser(requestUserLoginDto: RequestUserLoginDto): Result<Unit>

    suspend fun postKuCatch(requestKuCatchDto: RequestKuCatchDto) : Result<ResponseDto>
}