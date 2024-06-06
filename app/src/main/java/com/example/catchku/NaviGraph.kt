package com.example.catchku


import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catchku.screen.item.ItemScreen
import com.example.catchku.screen.ku.KuScreen
import com.example.catchku.screen.map.MapScreen
import com.example.catchku.screen.ranking.RankingScreen
import com.example.catchku.screen.HomeScreen
import com.example.catchku.screen.item.ItemScreenViewModel
import com.example.catchku.screen.ku.KuViewModel
import com.example.catchku.screen.login.LoginScreen
import com.example.catchku.screen.login.LoginViewModel
import com.example.catchku.screen.map.MapViewModel
import com.example.catchku.screen.ranking.RankingViewModel
import com.example.catchku.screen.signup.SignupScreen
import com.example.catchku.screen.signup.SignupViewModel

sealed class Routes(val route: String) {
    data object Login : Routes("Login")
    data object SignUp : Routes("SignUp")
    data object Home : Routes("Home")
    data object Ranking : Routes("Ranking")
    data object Map : Routes("Map")
    data object Item : Routes("Item")
    data object Ku : Routes("Ku")
}

inline fun <reified T> Array<out ViewModel>.getViewModel(): T {
    Log.d("[LOG]", "T: ${T::class.java}")
    return firstOrNull { it is T } as? T
        ?: throw Exception("ViewModel type: ${T::class.java} not found")
}

@Composable
fun NaviGraph(
    vararg viewModels: ViewModel,
    bottomBarVisible: (Boolean) -> Unit
) {
    val navStoreOwner = rememberViewModelStoreOwner()
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = Routes.Home.route) {

            composable(route = Routes.Home.route) {
                HomeScreen(navController = navController)
                bottomBarVisible(false)
            }

            composable(
                route = Routes.Login.route,
            ) {
                LoginScreen(
                    navController = navController,
                    bottomBarVisible = bottomBarVisible,
                    loginViewModel = viewModels.getViewModel<LoginViewModel>()
                )
            }

            composable(route = Routes.SignUp.route) {
                SignupScreen(
                    navController = navController,
                    signupViewModel = viewModels.getViewModel<SignupViewModel>()
                )
            }

            composable(route = Routes.Map.route) {
                MapScreen(
                    navController = navController,
                    mapViewModel = viewModels.getViewModel<MapViewModel>()
                )
            }

            composable(route = Routes.Item.route) {
                ItemScreen(
                    navController = navController,
                    itemScreenViewModel = viewModels.getViewModel<ItemScreenViewModel>()
                )
            }

            composable(route = Routes.Ku.route) {
                KuScreen(
                    navController = navController,
                    kuViewModel = viewModels.getViewModel<KuViewModel>()
                )
            }

            composable(route = Routes.Ranking.route) {
                RankingScreen(
                    navController = navController,
                    rankingViewModel = viewModels.getViewModel<RankingViewModel>()
                )
            }
        }
    }
}
