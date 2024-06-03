package com.example.catchku.data.repository

import com.example.catchku.data.model.request.RequestKuCatchDto
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.request.RequestUserObtainItemDto
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.request.RequestUserUseItemDto
import com.example.catchku.data.model.response.ResponseDto
import com.example.catchku.data.model.response.ResponseKuListDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseUserItemListDto
import com.example.catchku.data.model.response.ResponseTopFiveUserDto
import com.example.catchku.data.model.response.ResponseUserLoginDto
import com.example.catchku.data.source.UserDataSource
import com.example.catchku.domain.repository.UserRepository
import retrofit2.Call
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userCache: UserCache,
) : UserRepository {
    override suspend fun getTopFiveDepartment(): Result<ResponseTopFiveDepartmentDto> =
        runCatching {
            userDataSource.getKUTopFiveDepartment()
        }

    override suspend fun getKuList(userId: Int): Result<ResponseKuListDto> =
        runCatching {
            userDataSource.getKuList(userId)
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

    override suspend fun postKuCatch(requestKuCatchDto: RequestKuCatchDto): Result<Unit> =
       runCatching {
           userDataSource.postKUCatch(requestKuCatchDto)
       }

    override suspend fun getUserLoginId(): Int {
        return userCache.getSaveUserId()
    }

    override suspend fun getUserItemList(userId: Int): Result<ResponseUserItemListDto> =
        runCatching {
            userDataSource.getUserItemList(userId)
        }

    override suspend fun postObtainItem(requestUserObtainItemDto: RequestUserObtainItemDto):Result<ResponseDto> =
        runCatching {
            userDataSource.postObtainItem(requestUserObtainItemDto)
        }

    override suspend fun deleteUseItem(requestUserUseItemDto: RequestUserUseItemDto): Result<Unit> =
        runCatching {
            userDataSource.deleteUseItem(requestUserUseItemDto)
        }
}