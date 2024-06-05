package com.example.catchku.screen.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.model.response.ResponseUserLoginDto
import com.example.catchku.domain.repository.UserRepository
import com.example.catchku.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext context: Context,
) : ViewModel() {

    private val _postLoginUserState =
        MutableStateFlow<UiState<ResponseUserLoginDto>>(UiState.Loading)
    val postLoginUserState: StateFlow<UiState<ResponseUserLoginDto>> =
        _postLoginUserState.asStateFlow()

    var userId: Int = -1
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun postLoginUser(id: String, pw: String) {
        viewModelScope.launch {
            userRepository.postLoginUser(
                RequestUserLoginDto(
                    email = id,
                    password = pw
                )
            ).onSuccess { response ->
                _postLoginUserState.value = UiState.Success(response)
                Timber.e("성공 $response")
                userId = response.data.id
            }.onFailure { t ->
                Log.e("ABCD", "ViewModel 로그인 실패: ${t.message!!}")
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _postLoginUserState.value = UiState.Failure("${t.message}")
            }
        }
    }
}