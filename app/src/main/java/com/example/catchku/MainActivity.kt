package com.example.catchku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.catchku.ui.theme.CatchKUTheme
import com.sopt.now.compose.NaviGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatchKUTheme {
                val navController = rememberNavController()
                var isLoggedIn by remember { mutableStateOf(false) }

                NaviGraph(navController) { loggedIn ->
                    isLoggedIn = loggedIn
                }
            }
        }
    }
}
