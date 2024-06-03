package com.example.catchku.screen.ku

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.response.KuListInfo
import com.example.catchku.data.model.response.ResponseKuListDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
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
class KuViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userCache: UserCache
) : ViewModel() {

    private val _getKuList=
        MutableStateFlow<UiState<ResponseKuListDto>>(UiState.Loading)
    val getKuList: StateFlow<UiState<ResponseKuListDto>> = _getKuList.asStateFlow()

    private val _initUserId = MutableStateFlow<Int>(-12)
    val initUserId: StateFlow<Int> = _initUserId.asStateFlow()

    fun getUserId() {
        _initUserId.value = userCache.getSaveUserId()
    }

    fun getKuList(userId : Int) {
        viewModelScope.launch {
            userRepository.getKuList(userId)
                .onSuccess { response ->
                    _getKuList.value = UiState.Success(response)
                    Timber.e("성공 $response")
                }.onFailure { t ->
                    if (t is HttpException) {
                        val errorResponse = t.response()?.errorBody()?.string()
                        Timber.e("HTTP 실패: $errorResponse")
                    }
                    _getKuList.value = UiState.Failure("${t.message}")
                }
        }
    }
}