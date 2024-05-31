package com.example.catchku.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.catchku.ui.theme.CatchKUTheme
import com.example.catchku.Routes
import com.example.catchku.domain.entity.User


@Composable
fun SignupScreen(
    navController: NavController,
    signupViewModel: SignupViewModel
) {
    var textStudentEmail by remember { mutableStateOf("") }
    var textName by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var textDepartmentName by remember { mutableStateOf("") }
    val context = LocalContext.current


    fun signUp(textStudentEmail: String, textName: String, textPassword: String ,textDepartmentName: String) {
        if (isValidEmail(textStudentEmail) && textName.isNotEmpty() && textPassword.isNotEmpty() && textDepartmentName.isNotEmpty()){
            signupViewModel.postRegisterUser(User(textStudentEmail, textName, textPassword, textDepartmentName))
            Toast.makeText(context, "회원가입 성공", Toast.LENGTH_SHORT).show()
            navController.navigate(Routes.Login.route)
        }
    }

    CatchKUTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "회원가입")
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = textStudentEmail,
                onValueChange = { textStudentEmail = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = { Text("이메일") },
                placeholder = { Text("") },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = textName,
                onValueChange = { textName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = { Text("이름을 입력해주세요") },
                placeholder = { Text("") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = textPassword,
                onValueChange = { textPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = { Text("비밀번호") },
                placeholder = { Text("") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            TextField(
                value = textDepartmentName,
                onValueChange = { textDepartmentName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = { Text("전공학과") },
                placeholder = { Text("") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(120.dp))
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    signUp(textStudentEmail,textName,textPassword,textDepartmentName)
                }
            ) {
                Text(text = "회원가입 하기")
            }
        }
    }
}

private fun isValidEmail(email: String): Boolean {
    val validEmailRegex = Regex("^[^@]+@[^@]+$")
    return email.matches(validEmailRegex)
}

private fun isValidStudentNumber(studentNumber: String): Boolean {
    val validStudentNumberRegex = Regex("")
    return studentNumber.matches(validStudentNumberRegex)
}

private fun passwordCheck(password: String): Boolean {
    val passwordCheckRegex = Regex("")
    return password.matches(passwordCheckRegex)
}

