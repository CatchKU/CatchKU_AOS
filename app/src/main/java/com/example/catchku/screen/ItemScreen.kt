package com.example.catchku.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.catchku.R

data class Item(val image: Painter, val itemName: String, val itemDescription: String)

@Composable
fun ItemScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ITEMS",
            style = androidx.compose.ui.text.TextStyle(fontSize = 30.sp),
            modifier = Modifier.padding(30.dp),
            fontWeight = FontWeight.ExtraBold
        )
        Lazy_Item(init_item_data())
    }
}


@Composable
fun ItemCard(item: Item) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = item.image,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(128.dp)
        )
        Column {
            Text(
                text = item.itemName,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = item.itemDescription,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                ),

                )
        }
    }
}

@Composable
fun Lazy_Item(items: List<Item>) {
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        items(items) { item ->
            ItemCard(item = item)
        }
    }
}

@Composable
private fun init_item_data(): List<Item> {
    val items = listOf(
        Item(painterResource(id = R.drawable.computer_ku), "쿠", "공대생 쿠?"),
        Item(painterResource(id = R.drawable.computer_ku), "쿠", "공대생 쿠?"),
        Item(painterResource(id = R.drawable.computer_ku), "쿠", "공대생 쿠?"),
        Item(painterResource(id = R.drawable.computer_ku), "쿠", "공대생 쿠?"),
        Item(painterResource(id = R.drawable.computer_ku), "쿠", "공대생 쿠?")
    )
    return items
}

@Preview
@Composable
private fun ItemScreenPreview() {
    var navController = rememberNavController()
    ItemScreen(navController = navController)
}