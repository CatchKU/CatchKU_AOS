package com.example.catchku.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.domain.entity.User
import com.example.catchku.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun postRegisterUser(user: User) {
        viewModelScope.launch {
            userRepository.postRegisterUser(
                RequestUserRegisterDto(
                    email = user.email,
                    name = user.name,
                    password = user.password,
                    departmentName = user.departmentName
                )
            ).onSuccess { response ->
                Timber.e("성공 $response")
            }.onFailure { t ->
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
            }
        }
    }
}