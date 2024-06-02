package com.example.catchku.screen.ku

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key.Companion.K
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.catchku.R
import com.example.catchku.data.model.response.ResponseKuListDto
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.domain.entity.Department
import com.example.catchku.domain.entity.KuList
import com.example.catchku.util.UiState
import com.google.android.gms.common.config.GservicesValue.value

data class Ku(val image: Painter, val kuName: String)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun KuScreen(navController: NavHostController , kuViewModel: KuViewModel) {

    val lifecycleOwner = LocalLifecycleOwner
    val uiState by kuViewModel.getKuList
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    var getKuList by remember { mutableStateOf<List<KuList>>(emptyList()) }

    kuViewModel.getKuList(1) // userId 넣어주!

    fun mapper(value: ResponseKuListDto): List<KuList> {
        return value.data.map {
            KuList(
                kuName = it.kuName,
                catchedDate = it.catchedDate
            )
        }
    }

    when (uiState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            val data = (uiState as UiState.Success<ResponseKuListDto>).data
            getKuList = mapper(data)
        }
    }
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text = "잡은 쿠 리스트",
            modifier = Modifier.padding(30.dp),
            style = TextStyle(fontSize = 30.sp),
            fontWeight = FontWeight.ExtraBold
            )
        LazyGrid_Ku(init_item_data(),getKuList)
    }
}

@Composable
fun Ku_Card(item: Ku) {
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
            text = item.kuName,
            style = TextStyle(fontSize = 16.sp)
        )
    }
}

@Composable
fun LazyGrid_Ku(items: List<Ku>,kuList: List<KuList>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(items) { item ->
            kuList.forEach {
                if(it.kuName == item.kuName) {
                    Ku_Card(item = item)
                }
            }

        }
    }
}

@Composable
private fun init_item_data(): List<Ku> {
    val items = listOf(
        Ku(painterResource(id = R.drawable.ku), "쿠"),
        Ku(painterResource(id = R.drawable.computer_ku), "컴공 쿠"),
        Ku(painterResource(id = R.drawable.diving_ku), "다이빙 쿠"),
        Ku(painterResource(id = R.drawable.crying_catched_ku), " 우는 쿠"),
//        Ku(painterResource(id = R.drawable.ku), "그냥 쿠"),
//        Ku(painterResource(id = R.drawable.computer_ku), "공대 쿠"),
//        Ku(painterResource(id = R.drawable.diving_ku), "물안경 쿠"),
//        Ku(painterResource(id = R.drawable.crying_catched_ku), " 잡혀버린 쿠"),
//        Ku(painterResource(id = R.drawable.ku), "그냥 쿠")
    )
    return items
}

