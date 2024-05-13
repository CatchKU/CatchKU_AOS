package com.example.catchku.screen

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.materialIcon
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
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val cameraPositionState = rememberCameraPositionState()
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            locationSource = rememberFusedLocationSource(),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Follow
            ),
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true,
            ),
        ) {
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
            //입학정보관
            //산학협동관
            //수의학관
            //새천년관
            //건축관
            //해봉부동산학과
            //인문학관
            //학생회관
            //공학관
            SetMarker(latitude = 37.54165947462257, longitude = 127.07881987094879)
            //신공학관
            SetMarker(latitude = 37.54054930197007, longitude = 127.0794689655304)
            //과학관
            //창의관
            //KU기술혁신관
            //쿨하우스
            //건대부중
            //건대부고
            //건국대학교병원
            SetMarker(latitude = 37.54060, longitude = 127.0717)
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SetMarker(latitude: Double, longitude: Double) {
    Marker(
        state = MarkerState(position = LatLng(latitude, longitude)),
        icon = OverlayImage.fromResource(R.drawable.ku)
    )
}