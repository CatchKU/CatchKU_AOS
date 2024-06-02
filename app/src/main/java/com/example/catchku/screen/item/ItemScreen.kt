package com.example.catchku.screen.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Image(
            painter = item.image,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(128.dp)
        )
        Column {
            Text(
                text = item.itemName,
                modifier = Modifier.padding(top = 10.dp),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = item.itemDescription,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 15.sp
                ),
                color = Color(0xFFB5B6B7)

            )
        }
    }
}

@Composable
fun Lazy_Item(items: List<Item>) {
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        items(items = items) { item ->
            ItemCard(item = item)
        }
    }
}

@Composable
private fun init_item_data(): List<Item> {
    val items = listOf(
        Item(painterResource(id = R.drawable.proto1), "최강 다이아몬드", "매우 반짝입니다!"),
        Item(painterResource(id = R.drawable.proto2), "마법의 모자", "신비로운 모자.쓰면 무슨일이 일어날지도..?"),
        Item(painterResource(id = R.drawable.proto3), "마법의 물약", "의심스러운 물약. 마시면 좋은 일이 일어날 것 같다."),
        Item(painterResource(id = R.drawable.proto4), "지옥의 용광로", "무언가를 넣으면 새로운게 나올 것 같다.."),
    )
    return items
}

@Preview
@Composable
private fun ItemScreenPreview() {
    var navController = rememberNavController()
    ItemScreen(navController = navController)
}

@Preview
@Composable
private fun ItemCard() {
    ItemCard(item = Item(painterResource(id = R.drawable.computer_ku), "쿠", "공대생 쿠?"))
}