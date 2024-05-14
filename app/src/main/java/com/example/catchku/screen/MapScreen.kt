package com.example.catchku.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.catchku.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val cameraPositionState = rememberCameraPositionState()
        val currentLocation = rememberFusedLocationSource()

        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            locationSource = currentLocation,
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Follow
            ),
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true,
            ),
        ) {
            DrawMarker()
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SetMarker(latitude: Double, longitude: Double) {
    if (isVisible()) {
        Marker(
            //TODO: 랜덤하게 황금 쿠
            state = MarkerState(position = LatLng(latitude, longitude)),
            icon = OverlayImage.fromResource(R.drawable.ku)
        )
    } else {
        //Timer()
    }
}

fun isVisible(): Boolean {
    //다른 유저가 3분이내에 해당 쿠에 접근했나?
    if (true) {
        return true
    }
    else {
        return false
    }
}

@Composable
fun DrawMarker() {
    //행정관
    SetMarker(latitude = 37.5431505, longitude = 127.0751552)
    //경영관
    SetMarker(latitude = 37.5442615, longitude = 127.0760717)
    //상허연구관
    SetMarker(latitude = 37.5441682, longitude = 127.0753535)
    //교육과학관
    SetMarker(latitude = 37.5439837, longitude = 127.0742108)
    //예술문화관
    SetMarker(latitude = 37.542845, longitude = 127.0729332)
    //언어교육원
    SetMarker(latitude = 37.5426356, longitude = 127.074649)
    //박물관
    SetMarker(latitude = 37.5423945, longitude = 127.0756472)
    //법학관
    SetMarker(latitude = 37.5419017, longitude = 127.0749445)
    //상허기념도서관
    SetMarker(latitude = 37.5419226, longitude = 127.0737408)
    //의생명과학연구관
    SetMarker(latitude = 37.5415596, longitude = 127.0721872)
    //생명과학관
    SetMarker(latitude = 37.5407426, longitude = 127.0735979)
    //동물생명과학관
    SetMarker(latitude = 37.5403664, longitude = 127.0743614)
    //입학정보관
    SetMarker(latitude = 37.5402342, longitude = 127.0735998)
    //산학협동관
    SetMarker(latitude = 37.5396663, longitude = 127.0732309)
    //수의학관
    SetMarker(latitude = 37.5390954, longitude = 127.0747386)
    //새천년관
    SetMarker(latitude = 37.5435659, longitude = 127.0772119)
    //건축관
    SetMarker(latitude = 37.5434839, longitude = 127.0785437)
    //해봉부동산학과
    SetMarker(latitude = 37.5433009, longitude = 127.0782828)
    //인문학관
    SetMarker(latitude = 37.5424065, longitude = 127.0786945)
    //학생회관
    SetMarker(latitude = 37.5418772, longitude = 127.0782087)
    //공학관
    SetMarker(latitude = 37.541635, longitude = 127.0787904)
    //신공학관
    SetMarker(latitude = 37.5405464, longitude = 127.0794723)
    //과학관
    SetMarker(latitude = 37.5414841, longitude = 127.0804325)
    //창의관
    SetMarker(latitude = 37.5407625, longitude = 127.0793428)
    //KU기술혁신관
    SetMarker(latitude = 37.5397343, longitude = 127.0772939)
    //쿨하우스
    SetMarker(latitude = 37.5391834, longitude = 127.0780082)
    //건국대학교병원
    SetMarker(latitude = 37.5404895, longitude = 127.0719454)
}