package com.example.catchku.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.catchku.R
import com.example.catchku.ui.theme.CatchKUTheme

import com.example.catchku.Routes


@Composable
fun HomeScreen(navController: NavHostController) {
    var myName by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    CatchKUTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_background), // Replace R.drawable.background_image with your actual drawable resource ID
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(160.dp))
                Button(
                    onClick = {
                        Toast.makeText(context, "아직 구현 안함!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF1F855A))
                ) {
                    Text(text = "게임시작")
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(onClick = {
                    navController.navigate(Routes.Login.route)
                },
                    colors = ButtonDefaults.buttonColors(Color(0xFF1F855A))) {
                    Text(text = "로그인")
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(onClick = {
                    Toast.makeText(context, "아직 구현 안함!", Toast.LENGTH_SHORT).show()
                },
                    colors = ButtonDefaults.buttonColors(Color(0xFF1F855A))) {
                    Text(text = "언어설정 / Language")
                }
            }

        }
    }
}

