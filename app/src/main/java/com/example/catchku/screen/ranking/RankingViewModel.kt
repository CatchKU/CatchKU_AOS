package com.example.catchku.screen.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseTopFiveUserDto
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
class RankingViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _getTopFiveDepartmentState =
        MutableStateFlow<UiState<ResponseTopFiveDepartmentDto>>(UiState.Loading)
    val getTopFiveDepartmentState: StateFlow<UiState<ResponseTopFiveDepartmentDto>> =
        _getTopFiveDepartmentState.asStateFlow()

    private val _getTopFiveUserState =
        MutableStateFlow<UiState<ResponseTopFiveUserDto>>(UiState.Loading)
    val getTopFiveUserState: StateFlow<UiState<ResponseTopFiveUserDto>> =
        _getTopFiveUserState.asStateFlow()

    fun getTopFiveDepartment() {
        viewModelScope.launch {
            userRepository.getTopFiveDepartment()
                .onSuccess { response ->
                    _getTopFiveDepartmentState.value = UiState.Success(response)
                    Timber.e("성공 $response")
                }.onFailure { t ->
                    if (t is HttpException) {
                        val errorResponse = t.response()?.errorBody()?.string()
                        Timber.e("HTTP 실패: $errorResponse")
                    }
                    _getTopFiveDepartmentState.value = UiState.Failure("${t.message}")
                }
        }
    }

    fun getTopFiveUser() {
        viewModelScope.launch {
            userRepository.getTopFiveUser()
                .onSuccess { response ->
                    _getTopFiveUserState.value = UiState.Success(response)
                    Timber.e("성공 $response")
                }.onFailure { t ->
                    if (t is HttpException) {
                        val errorResponse = t.response()?.errorBody()?.string()
                        Timber.e("HTTP 실패: $errorResponse")
                    }
                    _getTopFiveUserState.value = UiState.Failure("${t.message}")
                }
        }
    }
}