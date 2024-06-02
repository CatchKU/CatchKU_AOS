package com.example.catchku.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.request.RequestKuCatchDto
import com.example.catchku.data.model.request.RequestUserLoginDto
import com.example.catchku.data.repository.UserCache
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
class MapViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userCache: UserCache,
): ViewModel() {
    private val _postKuCatchState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val postKuCatchState: StateFlow<UiState<Unit>> = _postKuCatchState.asStateFlow()

    var userId = userCache.getSaveUserId()

    fun postKuCatch(userId: Int, kuName: String) {
        viewModelScope.launch {
            userRepository.postKuCatch(
               RequestKuCatchDto(
                   userId,
                   kuName
               )
            ).onSuccess { response ->
                _postKuCatchState.value = UiState.Success(response)
                Timber.e("성공 $response")
            }.onFailure { t ->
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _postKuCatchState.value = UiState.Failure("${t.message}")
            }
        }
    }
}