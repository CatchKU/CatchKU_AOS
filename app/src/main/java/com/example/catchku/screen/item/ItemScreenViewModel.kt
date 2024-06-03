package com.example.catchku.screen.item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.response.ResponseKuListDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseUserItemListDto
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
class ItemScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userCache: UserCache
): ViewModel() {

    private val _getUserItemList=
        MutableStateFlow<UiState<ResponseUserItemListDto>>(UiState.Loading)
    val getUserItemList: StateFlow<UiState<ResponseUserItemListDto>> = _getUserItemList.asStateFlow()

    fun getUserId(): Int{
        return userCache.getSaveUserId()
    }

    fun getUserItemList(userId : Int) {
        viewModelScope.launch {
            userRepository.getUserItemList(userId)
                .onSuccess { response ->
                    _getUserItemList.value = UiState.Success(response)
                    Timber.e("성공 $response")
                }.onFailure { t ->
                    if (t is HttpException) {
                        val errorResponse = t.response()?.errorBody()?.string()
                        Timber.e("HTTP 실패: $errorResponse")
                    }
                    _getUserItemList.value = UiState.Failure("${t.message}")
                }
        }
    }


}