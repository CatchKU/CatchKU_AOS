package com.example.catchku.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.catchku.R

@Composable
fun KuScreen(navController: NavHostController) {
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "잡은 쿠 리스트",
            style = TextStyle(fontSize = 30.sp),
            fontWeight = FontWeight.ExtraBold
            )
        LazyGrid_Ku(init_item_data())
    }
}

@Composable
fun Ku_Card(item: Item) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = item.image,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(128.dp)
        )
        Text(
            text = item.text,
            style = TextStyle(fontSize = 16.sp)
        )
    }
}

@Composable
fun LazyGrid_Ku(items: List<Item>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(items) { item ->
            Ku_Card(item = item)
        }
    }
}

@Composable
private fun init_item_data(): List<Item> {
    val items = listOf(
        Item(painterResource(id = R.drawable.ku), "그냥 쿠"),
        Item(painterResource(id = R.drawable.computer_ku), "공대 쿠"),
        Item(painterResource(id = R.drawable.diving_ku), "물안경 쿠"),
        Item(painterResource(id = R.drawable.crying_catched_ku), " 잡혀버린 쿠"),
        Item(painterResource(id = R.drawable.ku), "그냥 쿠"),
        Item(painterResource(id = R.drawable.computer_ku), "공대 쿠"),
        Item(painterResource(id = R.drawable.diving_ku), "물안경 쿠"),
        Item(painterResource(id = R.drawable.crying_catched_ku), " 잡혀버린 쿠"),
        Item(painterResource(id = R.drawable.ku), "그냥 쿠")
    )
    return items
}

@Preview
@Composable
private fun ItemScreenPreview() {
    var navController = rememberNavController()
    KuScreen(navController = navController)
}