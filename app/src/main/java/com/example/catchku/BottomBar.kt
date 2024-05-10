package com.example.catchku

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sopt.now.compose.Routes


@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    visible: Boolean
) {

    val screens = listOf(
        BottomNavItem.Item, BottomNavItem.Ranking,BottomNavItem.Ku
    )

    if (visible) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBar(
            modifier = modifier,
            containerColor = Color.LightGray,
        ) {
            screens.forEach { screen ->
                NavigationBarItem(
                    label = {
                        Text(text = screen.title!!)
                    },
                    icon = {
                        Icon(imageVector = screen.icon!!, contentDescription = "")
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.Gray, selectedTextColor = Color.White
                    ),
                )
            }
        }
    }

}

sealed class BottomNavItem(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object Ranking : BottomNavItem(
        route = Routes.Ranking.route,
        title = "Ranking",
        icon = Icons.Default.Search
    )

    data object Item : BottomNavItem(
        route = Routes.Item.route,
        title = "Item",
        icon = Icons.Default.Person
    )
    data object Ku : BottomNavItem(
        route = Routes.Ku.route,
        title = "Ku",
        icon = Icons.Default.Person
    )
}
