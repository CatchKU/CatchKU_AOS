package com.example.catchku.data.repository

import android.util.Log
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseTopFiveUserDto
import com.example.catchku.data.model.response.ResponseUserLoginDto
import com.example.catchku.data.source.UserDataSource
import com.example.catchku.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userCache: UserCache,
) : UserRepository {
    override suspend fun getTopFiveDepartment(): Result<ResponseTopFiveDepartmentDto> =
        runCatching {
            userDataSource.getKUTopFiveDepartment()
        }

    override suspend fun getTopFiveUser(): Result<ResponseTopFiveUserDto> =
        runCatching {
            userDataSource.getTopFiveUser()
        }

    override suspend fun postRegisterUser(requestUserRegisterDto: RequestUserRegisterDto): Result<ResponseRegisterDto> =
        runCatching {
            userDataSource.postRegisterUser(requestUserRegisterDto)
        }

    override suspend fun postLoginUser(requestUserLoginDto: RequestUserLoginDto): Result<ResponseUserLoginDto> =
        runCatching {
            userDataSource.postLoginUser(requestUserLoginDto)
                .also {
                    userCache.saveUserId(it.data.id)
                }
        }



    override suspend fun getUserLoginId(): Int {
        return userCache.getSaveUserId()
    }
}