package com.example.catchku.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.request.RequestKuCatchDto
import com.example.catchku.data.model.request.RequestUserObtainItemDto
import com.example.catchku.data.model.response.ResponseDto
import com.example.catchku.data.repository.UserCache
import com.example.catchku.domain.entity.MarkerLocation
import com.example.catchku.domain.repository.UserRepository
import com.example.catchku.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
) : ViewModel() {
    private val _postKuCatchState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val postKuCatchState: StateFlow<UiState<Unit>> = _postKuCatchState.asStateFlow()

    private val _postUserObtainItemState = MutableStateFlow<UiState<ResponseDto>>(UiState.Loading)
    val postUserObtainItemState: StateFlow<UiState<ResponseDto>> =
        _postUserObtainItemState.asStateFlow()


    private val _initUserId = MutableStateFlow<Int>(-12)
    val initUserId: StateFlow<Int> = _initUserId.asStateFlow()

    // Marker visibility state
    private val _isMarkerVisible = MutableStateFlow(true)
    val isMarkerVisible: StateFlow<Boolean> = _isMarkerVisible.asStateFlow()

    fun getUserId() {
        _initUserId.value = userCache.getSaveUserId()
    }

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

    fun postUserObtainItem(userId: Int, itemName: String) {
        viewModelScope.launch {
            userRepository.postObtainItem(
                RequestUserObtainItemDto(
                    userId,
                    itemName
                )
            ).onSuccess { response ->
                _postUserObtainItemState.value = UiState.Success(response)
                Timber.e("성공 $response")
            }.onFailure { t ->
                if (t is HttpException) {
                    val errorResponse = t.response()?.errorBody()?.string()
                    Timber.e("HTTP 실패: $errorResponse")
                }
                _postUserObtainItemState.value = UiState.Failure("${t.message}")
            }
        }
    }

    private val _maxDistanceThreshold = MutableStateFlow(150f)
    val maxDistanceThreshold: StateFlow<Float> = _maxDistanceThreshold

    private val _catchDistanceThreshold = MutableStateFlow(30f)
    val catchDistanceThreshold: StateFlow<Float> = _catchDistanceThreshold

    fun updateMaxDistanceThreshold(value: Float) {
        viewModelScope.launch {
            _maxDistanceThreshold.value = value
            delay(8000)
            _maxDistanceThreshold.value = 150f
        }
    }

    fun updateCatchDistanceThreshold(value: Float) {
        viewModelScope.launch {
            _catchDistanceThreshold.value = value
            delay(8000)
            _catchDistanceThreshold.value = 30f
        }
    }

    fun hideMarkerFor10Seconds(markerLocation: MarkerLocation) {
        viewModelScope.launch {
            markerLocation.isVisible.value = false
            delay(10000)
            markerLocation.isVisible.value = true
        }
    }

}