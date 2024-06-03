package com.example.catchku.screen.item

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.catchku.R
import com.example.catchku.data.model.response.ItemInfo
import com.example.catchku.data.model.response.ResponseKuListDto
import com.example.catchku.data.model.response.ResponseUserItemListDto
import com.example.catchku.domain.entity.KuInfo
import com.example.catchku.util.UiState

data class Item( val itemName: String, val count: Int)

@SuppressLint("FlowOperatorInvokedInComposition", "StateFlowValueCalledInComposition")
@Composable
fun ItemScreen(navController: NavHostController, itemScreenViewModel: ItemScreenViewModel) {
    val lifecycleOwner = LocalLifecycleOwner
    val uiState by itemScreenViewModel.getUserItemList
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    var getUserItemList by remember { mutableStateOf<List<ItemInfo>>(emptyList()) }

    itemScreenViewModel.getUserId()
    itemScreenViewModel.getUserItemList(itemScreenViewModel.initUserId.value)


    fun mapper(value: ResponseUserItemListDto): List<ItemInfo> {
        return value.data.map {
            ItemInfo(
                itemName = it.itemName ,
                count = it.count
            )
        }
    }

    when (uiState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            val data = (uiState as UiState.Success<ResponseUserItemListDto>).data
            getUserItemList = mapper(data)
        }
    }


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
        Lazy_Item(getUserItemList)
    }
}


@Composable
fun ItemCard(item: ItemInfo) {
    var imageResource by remember {
        mutableIntStateOf(R.drawable.img_mapscreen_item1)
    }

    LaunchedEffect(item.itemName) {
        imageResource = when (item.itemName) {
            "쿠 레이더" -> R.drawable.img_mapscreen_item1
            else -> R.drawable.img_mapscreen_item2
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Image(
            painterResource(imageResource),
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
                text = "남은 개수 : ${item.count}개",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 15.sp
                ),
                color = Color(0xFFB5B6B7)

            )
        }
    }
}

@Composable
fun Lazy_Item(itemlist: List<ItemInfo>) {
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        items(itemlist) { item ->
            ItemCard(item)
        }
    }
}




