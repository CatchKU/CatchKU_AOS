package com.example.catchku.screen.item

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.example.catchku.R
import com.example.catchku.data.model.response.ResponseUserItemListDto
import com.example.catchku.domain.entity.Item
import com.example.catchku.screen.map.MapViewModel
import com.example.catchku.util.UiState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("FlowOperatorInvokedInComposition", "StateFlowValueCalledInComposition")
@Composable
fun ItemScreen(navController: NavHostController, itemScreenViewModel: ItemScreenViewModel,mapViewModel: MapViewModel) {
    val lifecycleOwner = LocalLifecycleOwner
    val uiItemListState by itemScreenViewModel.getUserItemList
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    val uiUseItemState by itemScreenViewModel.deleteUseItem
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    var getUserItemList by remember { mutableStateOf<List<Item>>(emptyList()) }

    itemScreenViewModel.getUserId()
    itemScreenViewModel.getUserItemList(itemScreenViewModel.initUserId.value)



    fun mapper(value: ResponseUserItemListDto): List<Item> {
        return value.data.map {
            Item(
                itemName = it.itemName,
                count = it.count
            )
        }
    }

    when (uiItemListState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            val data = (uiItemListState as UiState.Success<ResponseUserItemListDto>).data
            getUserItemList = mapper(data)
        }
    }

    @Composable
    fun observeStateChanges(mapViewModel: MapViewModel) {
        val maxDistanceThreshold by mapViewModel.maxDistanceThreshold.collectAsState()
        val catchDistanceThreshold by mapViewModel.catchDistanceThreshold.collectAsState()
        LaunchedEffect(maxDistanceThreshold, catchDistanceThreshold) {
            // Handle any side effects or UI updates here
        }
        // Use these values to update your UI as needed
    }

    observeStateChanges(mapViewModel)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CenterAlignedTopAppBar(title = {
            Text(
                text = "ITEMS",
                style = androidx.compose.ui.text.TextStyle(fontSize = 30.sp),
                modifier = Modifier.padding(30.dp),
                fontWeight = FontWeight.ExtraBold
            )
        })

        Lazy_Item(getUserItemList, itemScreenViewModel,mapViewModel)
    }
}



@Composable
fun ItemCard(item: Item, itemScreenViewModel: ItemScreenViewModel, mapViewModel: MapViewModel) {
    var imageResource by remember {
        mutableIntStateOf(R.drawable.img_mapscreen_item1)
    }

    var itemCount by remember(item.count) { mutableIntStateOf(item.count) }
    val coroutineScope = rememberCoroutineScope()

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
                text = "남은 개수 : ${itemCount}개",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 15.sp
                ),
                color = Color(0xFFB5B6B7)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "사용하기",
                modifier = Modifier.clickable {
                    if (itemCount > 0) {
                        itemCount--
                        itemScreenViewModel.deleteUseItem(
                            itemScreenViewModel.initUserId.value,
                            item.itemName
                        )

                        coroutineScope.launch {
                            if (item.itemName == "쿠 레이더") {
                                mapViewModel.updateMaxDistanceThreshold(200f)

                            } else {
                                mapViewModel.updateCatchDistanceThreshold(60f)

                            }
                        }
                    }
                })
        }
    }
}



@Composable
fun Lazy_Item(itemList: List<Item>, itemScreenViewModel: ItemScreenViewModel, mapViewModel: MapViewModel) {
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        items(itemList) { item ->
            ItemCard(item, itemScreenViewModel, mapViewModel)
        }
    }
}




