package com.example.catchku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.catchku.ui.theme.CatchKUTheme
import com.example.catchku.NaviGraph
import com.example.catchku.screen.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatchKUTheme {
                val navController = rememberNavController()
                var bottomBarVisible by remember { mutableStateOf(false) }
                val signupViewModel by viewModels<SignupViewModel>()

                Scaffold(
                    bottomBar = {
                        if (bottomBarVisible) {
                            BottomBar(navController = navController, bottomBarVisible = true)
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        NaviGraph(signupViewModel, navController) { visible ->
                            bottomBarVisible = visible
                        }
                    }
                }
            }
        }
    }
}
