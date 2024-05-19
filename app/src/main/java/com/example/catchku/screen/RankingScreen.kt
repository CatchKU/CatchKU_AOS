package com.example.catchku.screen

import android.view.View
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.catchku.R

data class Major(val major: String, val ku: Int)

@Composable
fun RankingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RankScreen()
    }
}

@Composable
fun RankScreen() {
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Graph()
        Rank_List()
    }
}

@Composable
fun Graph_Card(modifier: Modifier = Modifier, color: Color, major: String, rank: String ,height : Dp) {
    Column(
        modifier = Modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = rank,
            style = TextStyle(fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.padding(top = 8.dp, bottom = 10.dp)
        )
       
        Column (modifier=Modifier.size(width =70.dp, height = height ).background(color,shape = RoundedCornerShape(8.dp)),
            ){

        }
        Text(
            text = major,
            style = TextStyle(fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun Graph(modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Graph_Card(color = Color(0xFFFFD3E3), major = "컴공", rank = "1",height=210.dp)
        Graph_Card(color = Color(0xFFD3FFF0), major = "산공", rank = "2",height=140.dp)
        Graph_Card(color = Color(0xFFEEE1FF), major = "화공", rank = "3",height=70.dp)
    }
}

@Composable
fun Rank_List(modifier: Modifier = Modifier) {
    Column(Modifier.padding( 10.dp)) {
        Rank_List_Card(rank = "1", major = "컴퓨터공학부", number = "200")
        Spacer(modifier = Modifier.height(8.dp))
        Rank_List_Card(rank = "2", major = "산업공학과", number = "150")
        Spacer(modifier = Modifier.height(8.dp))
        Rank_List_Card(rank = "3", major = "화학공학과", number = "100")
        Spacer(modifier = Modifier.height(8.dp))
        Rank_List_Card(rank = "4", major = "전기전자공학부", number = "70")
        Spacer(modifier = Modifier.height(8.dp))
        Rank_List_Card(rank = "5", major = "기계항공우주공학부", number = "50")
        Spacer(modifier = Modifier.height(8.dp))
        Rank_List_Card(rank = "6", major = "사회환경공학부", number = "15")
    }
}

@Composable
fun Rank_List_Card(modifier: Modifier = Modifier, rank: String, major: String, number: String) {
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
            style = TextStyle(fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
                .weight(1f)
        )
        Text(
            text = major,
            style = TextStyle(fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = number+" 명",
            style = TextStyle(fontSize = 16.sp
            ,fontWeight = FontWeight.Bold),
            color = Color(0xFFAEAFB0),
            modifier = Modifier
                .padding(start = 60.dp)
                .weight(1f),

            )
    }
}

@Preview
@Composable
private fun RankingScreenPreview() {
    var navController = rememberNavController()
    RankingScreen(navController = navController)
}

@Preview
@Composable
private fun List() {
    Rank_List_Card(modifier = Modifier, "1", "컴퓨터공학부", "20")
}
