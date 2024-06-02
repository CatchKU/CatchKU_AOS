package com.example.catchku.domain.repository

import com.example.catchku.data.model.request.RequestKuCatchDto
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseDto
import com.example.catchku.data.model.response.ResponseKuListDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseUserItemListDto

interface UserRepository {
    suspend fun getTopFiveDepartment(): Result<ResponseTopFiveDepartmentDto>

    suspend fun getKuList(userId: Int): Result<ResponseKuListDto>

    suspend fun postRegisterUser(requestUserRegisterDto: RequestUserRegisterDto): Result<ResponseRegisterDto>

    suspend fun postLoginUser(requestUserLoginDto: RequestUserLoginDto): Result<Unit>

    suspend fun postKuCatch(requestKuCatchDto: RequestKuCatchDto): Result<Unit>

    suspend fun getUserItemList(userId: Int): ResponseUserItemListDto
}