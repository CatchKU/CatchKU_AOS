package com.example.catchku.screen.ranking

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.example.catchku.data.model.response.ResponseTopFiveDepartmentDto
import com.example.catchku.data.model.response.ResponseTopFiveUserDto
import com.example.catchku.domain.entity.Department
import com.example.catchku.domain.entity.UserRanking
import com.example.catchku.util.UiState

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun RankingScreen(navController: NavHostController, rankingViewModel: RankingViewModel) {

    var isDepartmentScreen by remember { mutableStateOf<Boolean>(true) }

    val lifecycleOwner = LocalLifecycleOwner
    val departmentState by rankingViewModel.getTopFiveDepartmentState
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)
    
    val userState by rankingViewModel.getTopFiveUserState
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    rankingViewModel.getTopFiveDepartment()
    rankingViewModel.getTopFiveUser()

    var getDepartments by remember { mutableStateOf<List<Department>>(emptyList()) }
    var getUsers by remember { mutableStateOf<List<UserRanking>>(emptyList()) }

    fun departmentMapper(value: ResponseTopFiveDepartmentDto): List<Department> {
        return value.data.map { dto ->
            Department(
                departmentName = dto.departmentName,
                kuCount = dto.kuCount
            )
        }
    }
    
    fun userMapper(value: ResponseTopFiveUserDto): List<UserRanking> {
        return value.data.map {dto ->
            UserRanking(
                userId = dto.userId,
                userName = dto.userName,
                kuCount = dto.kuCount
            )
        }
    }

    when (departmentState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            val data = (departmentState as UiState.Success<ResponseTopFiveDepartmentDto>).data
            getDepartments = departmentMapper(data)
        }
    }
    
    when (userState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            val data = (userState as UiState.Success<ResponseTopFiveUserDto>).data
            getUsers = userMapper(data)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { isDepartmentScreen = true }) {
                Text(text = "Department Rankings")
            }
            Button(onClick = { isDepartmentScreen = false }) {
                Text(text = "User Rankings")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isDepartmentScreen) {
            Department_RankScreen(getDepartments)
        } else {
            User_RankScreen(getUsers)
        }
    }
}

@Composable
fun Department_RankScreen(departments: List<Department>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Graph(departments)
        Rank_List(departments)
    }
}

@Composable
fun User_RankScreen(users: List<UserRanking>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        userGraph(users)
        user_Rank_List(users)
    }
}

@Composable
fun Graph_Card(
    modifier: Modifier = Modifier,
    color: Color,
    major: String,
    rank: String,
    height: Dp
) {
    Column(
        modifier = Modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = rank,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 10.dp)
        )

        Column(
            modifier = Modifier
                .size(width = 70.dp, height = height)
                .background(color, shape = RoundedCornerShape(8.dp)),
        ) {

        }
        Text(
            text = major,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun userGraph(users: List<UserRanking>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        users.forEachIndexed { index, user ->
            Graph_Card(
                color = when (index) {
                    0 -> Color(0xFFFFD3E3)
                    1 -> Color(0xFFD3FFF0)
                    2 -> Color(0xFFEEE1FF)
                    else -> Color.Gray
                },
                major = user.userName,
                rank = (index + 1).toString(),
                height = (210.dp - (index * 70.dp)).coerceAtLeast(70.dp)
            )
        }
    }
}

@Composable
fun user_Rank_List(users: List<UserRanking>) {
    Column(Modifier.padding(10.dp)) {
        users.forEachIndexed { index, user ->
            Rank_List_Card(
                rank = (index + 1).toString(),
                major = user.userName,
                number = user.kuCount.toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Graph(departments: List<Department>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        departments.forEachIndexed { index, department ->
            Graph_Card(
                color = when (index) {
                    0 -> Color(0xFFFFD3E3)
                    1 -> Color(0xFFD3FFF0)
                    2 -> Color(0xFFEEE1FF)
                    else -> Color.Gray
                },
                major = department.departmentName,
                rank = (index + 1).toString(),
                height = (210.dp - (index * 70.dp)).coerceAtLeast(70.dp)
            )
        }
    }
}

@Composable
fun Rank_List(departments: List<Department>) {
    Column(Modifier.padding(10.dp)) {
        departments.forEachIndexed { index, department ->
            Rank_List_Card(
                rank = (index + 1).toString(),
                major = department.departmentName,
                number = department.kuCount.toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Rank_List_Card(rank: String, major: String, number: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFFF7F8F9), shape = RoundedCornerShape(8.dp))
            .padding(start = 10.dp)

    ) {
        Text(
            text = rank,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
                .weight(1f)
        )
        Text(
            text = major,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = number + " 마리",
            style = TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.Bold
            ),
            color = Color(0xFFAEAFB0),
            modifier = Modifier
                .padding(start = 60.dp)
                .weight(1f),

            )
    }
}
