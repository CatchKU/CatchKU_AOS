package com.example.catchku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatchKUTheme {
                val navController = rememberNavController()
                var bottomBarVisible by remember { mutableStateOf(false) }

                Scaffold(
                    bottomBar = {
                        if (bottomBarVisible) {
                            BottomBar(navController = navController, visible = true)
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        NaviGraph(navController) { visible ->
                            bottomBarVisible = visible
                        }
                    }
                }
            }
        }
    }
}
