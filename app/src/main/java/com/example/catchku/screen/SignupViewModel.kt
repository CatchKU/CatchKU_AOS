package com.example.catchku.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.data.model.response.ResponseRegisterDto
import com.example.catchku.domain.entity.User
import com.example.catchku.domain.repository.UserRepository
import com.example.catchku.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    fun postRegisterUser(user: User) {
        viewModelScope.launch {
            userRepository.postRegisterUser(
                RequestUserRegisterDto(
                    user.email,
                    user.name,
                    user.password,
                    user.departmentName
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