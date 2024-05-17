package com.example.catchku.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.catchku.R

data class Major(val major: String , val ku : Int)
@Composable
fun RankingScreen(navController: NavHostController) {
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Row {
            RankScreen()
        }
    }
}

@Composable
fun RankScreen() {

}

@Composable
private fun init_item_ranking(): List<Item> {
    val items = listOf(
        Item(painterResource(id = R.drawable.first_graph), "그냥 쿠"),
        Item(painterResource(id = R.drawable.second_graph), "공대 쿠"),
        Item(painterResource(id = R.drawable.diving_ku), "물안경 쿠")
    )
    return items
}

@Composable
fun Graph(modifier: Modifier = Modifier , graph : Painter , major : String) {
    Column {
        Image(
            painter = graph,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(128.dp)
        )
        Text(
            text = major,
            style = TextStyle(fontSize = 16.sp)
        )
    }
}
@Preview
@Composable
private fun RankingScreenPreview() {
    var navController = rememberNavController()
    RankingScreen(navController = navController)
}

