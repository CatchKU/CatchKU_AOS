package com.example.catchku.screen

import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.catchku.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CameraPositionState
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
import androidx.compose.runtime.*

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(navController: NavHostController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
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
            DrawMarker(cameraPositionState)
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SetMarker(latitude: Double, longitude: Double) {
    val isVisible = remember(latitude, longitude) {
        isVisible(latitude, longitude)
    }

    if (isVisible) {
        Marker(
            state = MarkerState(position = LatLng(latitude, longitude)),
            icon = OverlayImage.fromResource(R.drawable.ku)
        )
    }
}

// 건국대학교의 주요 장소들에 대한 마커를 추가합니다.
@Composable
fun DrawMarker(cameraPositionState: CameraPositionState) {
    // 사용자의 현재 위치
    val userLocation = cameraPositionState.position.target

    // 건국대학교의 주요 장소들의 위치를 정의합니다.
    val markerLocations = listOf(
        LatLng(37.5431505, 127.0751552), // 행정관
        LatLng(37.5442615, 127.0760717), // 경영관
        LatLng(37.5441682, 127.0753535), // 상허연구관
        LatLng(37.5439837, 127.0742108), // 교육과학관
        LatLng(37.542845, 127.0729332),  // 예술문화관
        LatLng(37.5426356, 127.074649),  // 언어교육원
        LatLng(37.5423945, 127.0756472), // 박물관
        LatLng(37.5419017, 127.0749445), // 법학관
        LatLng(37.5419226, 127.0737408), // 상허기념도서관
        LatLng(37.5415596, 127.0721872), // 의생명과학연구관
        LatLng(37.5407426, 127.0735979), // 생명과학관
        LatLng(37.5403664, 127.0743614), // 동물생명과학관
        LatLng(37.5402342, 127.0735998), // 입학정보관
        LatLng(37.5396663, 127.0732309), // 산학협동관
        LatLng(37.5390954, 127.0747386), // 수의학관
        LatLng(37.5435659, 127.0772119), // 새천년관
        LatLng(37.5434839, 127.0785437), // 건축관
        LatLng(37.5433009, 127.0782828), // 해봉부동산학과
        LatLng(37.5424065, 127.0786945), // 인문학관
        LatLng(37.5418772, 127.0782087), // 학생회관
        LatLng(37.541635, 127.0787904),  // 공학관
        LatLng(37.5405464, 127.0794723), // 신공학관
        LatLng(37.5414841, 127.0804325), // 과학관
        LatLng(37.5407625, 127.0793428), // 창의관
        LatLng(37.5397343, 127.0772939), // KU기술혁신관
        LatLng(37.5391834, 127.0780082), // 쿨하우스
        LatLng(37.5404895, 127.0719454)  // 건국대학교병원
    )

    // 각 장소의 위치와 사용자의 위치 간의 거리를 계산하여 일정 거리 이내에 있는 경우에만 마커를 표시합니다.
    markerLocations.forEach { location ->
        val distance = calculateDistance(location, userLocation)
        if (distance <= MAX_DISTANCE_THRESHOLD) {
            SetMarker(location.latitude, location.longitude)
        }
    }
}

// 두 위치 간의 거리를 계산합니다.
fun calculateDistance(location1: LatLng, location2: LatLng): Float {
    val results = FloatArray(1)
    Location.distanceBetween(
        location1.latitude, location1.longitude,
        location2.latitude, location2.longitude,
        results
    )
    return results[0]
}

// 해당 위치에 마커를 표시할지 여부를 결정합니다.
fun isVisible(latitude: Double, longitude: Double): Boolean {
    // 사용자와의 거리를 계산합니다
    // 여기에 사용자와의 거리를 계산하는 로직을 구현할 수 있습니다.
    return true // 임시로 모든 위치에 마커를 표시합니다.
}

// 마커를 표시할 최대 거리를 설정합니다.
private const val MAX_DISTANCE_THRESHOLD = 150f // 100미터로 설정하겠습니다. 필요에 따라 조절하세요.
