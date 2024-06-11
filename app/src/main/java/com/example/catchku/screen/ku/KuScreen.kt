package com.example.catchku.screen.ku

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.example.catchku.R
import com.example.catchku.data.model.response.ResponseKuListDto
import com.example.catchku.domain.entity.KuInfo
import com.example.catchku.util.UiState

data class Ku(val image: Painter, val kuName: String)

@SuppressLint("FlowOperatorInvokedInComposition", "StateFlowValueCalledInComposition")
@Composable
fun KuScreen(navController: NavHostController, kuViewModel: KuViewModel) {

    val lifecycleOwner = LocalLifecycleOwner
    val uiState by kuViewModel.getKuList
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    var getKuList by remember { mutableStateOf<List<KuInfo>>(emptyList()) }

    kuViewModel.getUserId()
    kuViewModel.getKuList(kuViewModel.initUserId.value)

    fun mapper(value: ResponseKuListDto): List<KuInfo> {
        return value.data.map {
            KuInfo(
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Catched KU List",
            modifier = Modifier.padding(30.dp),
            style = TextStyle(fontSize = 30.sp),
            fontWeight = FontWeight.ExtraBold
        )
        LazyGrid_Ku(getKuList)
    }
}

@Composable
fun Ku_Card(item: Ku) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
fun LazyGrid_Ku(kuList: List<KuInfo>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(kuList) { ku ->
            when (ku.kuName) {
                "쿠" -> Ku_Card(Ku(painterResource(id = R.drawable.ku), "Normal KU"))
                "공대 쿠" -> Ku_Card(Ku(painterResource(id = R.drawable.computer_ku), "Engineer Ku"))
                "다이빙 쿠" -> Ku_Card(Ku(painterResource(id = R.drawable.diving_ku), "Diving Ku"))
                "우는 쿠" -> Ku_Card(Ku(painterResource(id = R.drawable.crying_catched_ku), "Crying KU"))
            }
        }
    }
}

