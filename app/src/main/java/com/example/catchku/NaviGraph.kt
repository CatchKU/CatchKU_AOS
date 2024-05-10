package com.sopt.now.compose


import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.catchku.LocalNavGraphViewModelStoreOwner
import com.example.catchku.rememberViewModelStoreOwner
import com.sopt.now.compose.screen.HomeScreen
import com.sopt.now.compose.screen.LoginScreen
import com.example.catchku.screen.SignupScreen

sealed class Routes(val route: String) {
    data object Login : Routes("Login")
    data object SignUp : Routes("SignUp")

    data object Home : Routes("Home")


}

@Composable
fun NaviGraph(
    navController: NavHostController, onLoginSuccess: (Boolean) -> Unit
) {
    val navStoreOwner = rememberViewModelStoreOwner()
    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = Routes.Home.route) {

            composable(route = Routes.Home.route) {
                HomeScreen(navController = navController)
            }

            composable(
                route = Routes.Login.route,
            ) {
                LoginScreen(
                    navController = navController, onLoginSuccess = onLoginSuccess
                )
            }

            composable(route = Routes.SignUp.route) {
                SignupScreen(navController = navController)
            }
        }
    }
}
