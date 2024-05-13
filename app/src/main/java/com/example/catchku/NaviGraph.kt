package com.example.catchku


import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.catchku.screen.ItemScreen
import com.example.catchku.screen.KuScreen
import com.example.catchku.screen.MapScreen
import com.example.catchku.screen.RankingScreen
import com.example.catchku.screen.HomeScreen
import com.example.catchku.screen.LoginScreen
import com.example.catchku.screen.SignupScreen

sealed class Routes(val route: String) {
    data object Login : Routes("Login")
    data object SignUp : Routes("SignUp")
    data object Home : Routes("Home")
    data object Ranking : Routes("Ranking")
    data object Map : Routes("Map")
    data object Item : Routes("Item")
    data object Ku : Routes("Ku")
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

            composable(route = Routes.Map.route) {
                MapScreen(navController = navController)
            }

            composable(route = Routes.Item.route) {
                ItemScreen(navController = navController)
            }

            composable(route = Routes.Ku.route) {
                KuScreen(navController = navController)
            }

            composable(route = Routes.Ranking.route) {
                RankingScreen(navController = navController)
            }
        }
    }
}
