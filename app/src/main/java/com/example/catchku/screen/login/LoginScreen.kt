package com.example.catchku.screen.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.catchku.util.showToast
import com.unity3d.player.UnityPlayerActivity

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
    val context = LocalContext.current
    val uiState by loginViewModel.postLoginUserState
        .flowWithLifecycle(
            lifecycle = lifecycleOwner.current.lifecycle,
            minActiveState = Lifecycle.State.CREATED
        )
        .collectAsState(initial = UiState.Empty)

    when (val state = uiState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> {
            context.showToast(message = state.msg)
        }
        is UiState.Loading -> Unit
        is UiState.Success -> {
            bottomBarVisible(true)
            navController.navigate(Routes.Map.route) {
                popUpTo(Routes.Home.route) {
                    inclusive = true
                }
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
            Text(text = "Login")
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
                Text(text = "Login")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    navController.navigate(Routes.SignUp.route)
                }
            ) {
                Text(text = "Sign Up")
            }
//            Button(
//                modifier = Modifier.padding(10.dp),
//                onClick = {
//                    (context as? Activity)?.let {
//                        it.startActivity(
//                            Intent(
//                                /* packageContext = */ it,
//                                /* cls = */ UnityPlayerActivity::class.java
//                            )
//                        )
//                    }
//                }
//            ) {
//                Text(text = "유니티 테스트 버튼")
//            }
        }
    }
}
