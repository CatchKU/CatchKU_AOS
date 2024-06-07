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

@Composable
fun NaviGraph(
    signupViewModel: SignupViewModel,
    loginViewModel: LoginViewModel,
    rankingViewModel: RankingViewModel,
    mapViewModel: MapViewModel,
    kuViewModel: KuViewModel,
    itemScreenViewModel: ItemScreenViewModel,
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit
) {
    val navStoreOwner = rememberViewModelStoreOwner()
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
                bottomBarVisible(false)
                LoginScreen(
                    navController = navController,
                    bottomBarVisible = bottomBarVisible,
                    loginViewModel = loginViewModel
                )
            }

            composable(route = Routes.SignUp.route) {
                SignupScreen(navController, signupViewModel)
            }

            composable(route = Routes.Map.route) {
                bottomBarVisible(true)
                MapScreen(navController,mapViewModel)
            }

            composable(route = Routes.Item.route) {
                bottomBarVisible(true)
                ItemScreen(navController,itemScreenViewModel, mapViewModel)
            }

            composable(route = Routes.Ku.route) {
                bottomBarVisible(true)
                KuScreen(navController, kuViewModel)
            }

            composable(route = Routes.Ranking.route) {
                bottomBarVisible(true)
                RankingScreen(navController, rankingViewModel)
            }
        }
    }
}
