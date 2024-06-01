package com.example.catchku.data.repository

import com.example.catchku.data.model.request.RequestKuCatchDto
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.source.UserDataSource
import com.example.catchku.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getTopFiveDepartment(): Result<ResponseTopFiveDepartmentDto> =
        runCatching {
            userDataSource.getKUTopFiveDepartment()
        }

    override suspend fun postRegisterUser(requestUserRegisterDto: RequestUserRegisterDto): Result<ResponseRegisterDto> =
        runCatching {
            userDataSource.postRegisterUser(requestUserRegisterDto)
        }

    override suspend fun postLoginUser(requestUserLoginDto: RequestUserLoginDto): Result<Unit> =
        runCatching {
            userDataSource.postLoginUser(requestUserLoginDto)
        }

    override suspend fun postKuCatch(requestKuCatchDto: RequestKuCatchDto): Result<Unit> =
       runCatching {
           userDataSource.postKUCatch(requestKuCatchDto)
       }

}