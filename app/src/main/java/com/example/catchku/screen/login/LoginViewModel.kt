package com.example.catchku.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.request.RequestUserLoginDto
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
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _postLoginUserState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val postLoginUserState: StateFlow<UiState<Unit>> = _postLoginUserState.asStateFlow()

    fun postLoginUser(id: String, pw: String) {
        viewModelScope.launch {
            userRepository.postLoginUser(
                RequestUserLoginDto(
                    id,
                    pw
                )
            ).onSuccess { response ->
                _postLoginUserState.value = UiState.Success(response)
                Timber.e("성공 $response")
            }.onFailure { t ->
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _postLoginUserState.value = UiState.Failure("${t.message}")
            }
        }
    }
}