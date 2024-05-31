package com.example.catchku.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchku.data.model.request.RequestUserRegisterDto
import com.example.catchku.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    fun postRegisterUser() {
        viewModelScope.launch {
            userRepository.postRegisterUser(
                RequestUserRegisterDto(
                    "kelsey84@naver.com",
                    "su",
                    "0000",
                    "cse"
                )
            ).onSuccess { response ->
                Log.d("abcd", "성공")
            }.onFailure { t ->
                Log.d("abcd", "실패")
            }
        }
    }

}