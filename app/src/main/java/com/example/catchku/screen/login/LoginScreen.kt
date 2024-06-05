package com.example.catchku.screen.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.example.catchku.Routes
import com.example.catchku.ui.theme.CatchKUTheme
import com.example.catchku.util.UiState

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun LoginScreen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
    loginViewModel: LoginViewModel
) {
    var textId by remember { mutableStateOf("") }
    var textPw by remember { mutableStateOf("") }
    val lifecycleOwner = LocalLifecycleOwner
    val uiState by loginViewModel.postLoginUserState
        .flowWithLifecycle(
            lifecycle = lifecycleOwner.current.lifecycle,
            minActiveState = Lifecycle.State.CREATED
        )
        .collectAsState(initial = UiState.Empty)

    when (uiState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            bottomBarVisible(true)
            navController.navigate(Routes.Map.route) {
                popUpTo(Routes.Home.route)
            }
        }
    }

    fun login(textId: String, textPw: String) {
        loginViewModel.postLoginUser(textId, textPw)
    }

    CatchKUTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "로그인")
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = textId,
                onValueChange = { textId = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = { Text("ID") },
                placeholder = { Text("") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                    }
                )
            )
            TextField(
                value = textPw,
                onValueChange = { textPw = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = { Text("PASSWORD") },
                placeholder = { Text("") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(120.dp))

            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    login(textId, textPw)
                }
            ) {
                Text(text = "로그인")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    navController.navigate(Routes.SignUp.route)
                }
            ) {
                Text(text = "회원가입")
            }
        }
    }
}
