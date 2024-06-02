package com.example.catchku.screen.map

import android.annotation.SuppressLint
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.example.catchku.R
import com.example.catchku.domain.entity.MarkerLocation
import com.example.catchku.util.UiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CircleOverlay
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

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(navController: NavHostController,mapViewModel: MapViewModel) {
    val lifecycleOwner = LocalLifecycleOwner
    val uiState by mapViewModel.postKuCatchState
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val markerState = remember {
            mutableStateOf<LatLng?>(null)
        }

        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState(),
            locationSource = rememberFusedLocationSource(),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Follow
            ),
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true,
            ),
            onLocationChange = { location ->
                markerState.value = LatLng(location.latitude, location.longitude)
            },
        ) {
            markerState.value?.let {
                DrawKuMarker(currLocation = it,mapViewModel)
                DrawUserMarker(currLocation = it)
            }
        }
    }
}


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SetMarker(
    markerLocation: MarkerLocation,
    boundary: Boolean,
    mapViewModel: MapViewModel
) {
    val context = LocalContext.current
    Marker(
        state = MarkerState(position = LatLng(markerLocation.latLng.latitude,markerLocation.latLng.longitude)),
        icon = OverlayImage.fromResource(markerLocation.KuId),
        width = 30.dp,
        height = 30.dp,
        onClick = {
            if(boundary){
                mapViewModel.postKuCatch(0,markerLocation.kuName)
                true
            }else{
                Toast.makeText(context, "너무 멀어요", Toast.LENGTH_SHORT).show()
                false

            }
        }
    )
}


@Composable
fun DrawUserMarker(currLocation: LatLng) {

    // 사용자의 현재 위치
    CircleOverlay(
        center = LatLng(currLocation.latitude, currLocation.longitude),
        Color.Red.copy(alpha = 0.3F),
        150.0
    )

    CircleOverlay(
        center = LatLng(currLocation.latitude, currLocation.longitude),
        color = Color(0xFFCC0000),
        50.0
    )
}


@Composable
fun DrawKuMarker(currLocation: LatLng, mapViewModel: MapViewModel) {
    // 사용자의 현재 위치
    val userLocation = currLocation

    val markerLocations: List<MarkerLocation> = listOf(
        MarkerLocation(LatLng(37.5431505, 127.0751552), R.drawable.ku,"쿠"), // 행정관
        MarkerLocation(LatLng(37.5442615, 127.0760717), R.drawable.computer_ku,"컴공 쿠"), // 경영관
        MarkerLocation(LatLng(37.5441682, 127.0753535), R.drawable.crying_catched_ku,"우는 쿠"), // 상허연구관
        MarkerLocation(LatLng(37.5439837, 127.0742108), R.drawable.diving_ku,"다이빙 쿠"), // 교육과학관
        MarkerLocation(LatLng(37.542845, 127.0729332), R.drawable.ku,"쿠"),  // 예술문화관
        MarkerLocation(LatLng(37.5426356, 127.074649), R.drawable.computer_ku,"컴공 쿠"),  // 언어교육원
        MarkerLocation(LatLng(37.5423945, 127.0756472), R.drawable.crying_catched_ku,"우는 쿠"), // 박물관
        MarkerLocation(LatLng(37.5419017, 127.0749445), R.drawable.diving_ku,"다이빙 쿠"), // 법학관
        MarkerLocation(LatLng(37.5419226, 127.0737408), R.drawable.ku,"쿠"), // 상허기념도서관
        MarkerLocation(LatLng(37.5415596, 127.0721872), R.drawable.computer_ku,"컴공 쿠"), // 의생명과학연구관
        MarkerLocation(LatLng(37.5407426, 127.0735979), R.drawable.crying_catched_ku,"우는 쿠"), // 생명과학관
        MarkerLocation(LatLng(37.5403664, 127.0743614), R.drawable.diving_ku,"다이빙 쿠"), // 동물생명과학관
        MarkerLocation(LatLng(37.5402342, 127.0735998), R.drawable.ku,"쿠"), // 입학정보관
        MarkerLocation(LatLng(37.5396663, 127.0732309), R.drawable.computer_ku,"컴공 쿠"), // 산학협동관
        MarkerLocation(LatLng(37.5390954, 127.0747386), R.drawable.crying_catched_ku,"우는 쿠"), // 수의학관
        MarkerLocation(LatLng(37.5435659, 127.0772119), R.drawable.diving_ku,"다이빙 쿠"), // 새천년관
        MarkerLocation(LatLng(37.5434839, 127.0785437), R.drawable.ku,"쿠"), // 건축관
        MarkerLocation(LatLng(37.5433009, 127.0782828), R.drawable.computer_ku,"컴공 쿠"), // 해봉부동산학과
        MarkerLocation(LatLng(37.5424065, 127.0786945), R.drawable.crying_catched_ku,"우는 쿠"), // 인문학관
        MarkerLocation(LatLng(37.5418772, 127.0782087), R.drawable.diving_ku,"다이빙 쿠"), // 학생회관
        MarkerLocation(LatLng(37.541635, 127.0787904), R.drawable.ku,"쿠"),  // 공학관
        MarkerLocation(LatLng(37.5405464, 127.0794723), R.drawable.computer_ku,"컴공 쿠"), // 신공학관
        MarkerLocation(LatLng(37.5414841, 127.0804325), R.drawable.crying_catched_ku,"우는 쿠"), // 과학관
        MarkerLocation(LatLng(37.5407625, 127.0793428), R.drawable.diving_ku,"다이빙 쿠"), // 창의관
        MarkerLocation(LatLng(37.5397343, 127.0772939), R.drawable.ku,"쿠"), // KU기술혁신관
        MarkerLocation(LatLng(37.5391834, 127.0780082), R.drawable.computer_ku,"컴공 쿠"), // 쿨하우스
        MarkerLocation(LatLng(37.5404895, 127.0719454), R.drawable.crying_catched_ku,"우는 쿠") // 건국대학교병원
    )

    // 사용자 반경 내 쿠만 표시
    markerLocations.forEach { location ->
        val distance =
            calculateDistance(
                location.latLng,
                userLocation
            )
        if (distance <= MAX_DISTANCE_THRESHOLD && distance > CATCH__DISTANCE_THRESHOLD) {
            SetMarker(
                location,
                boundary = false,
                mapViewModel
            )
        } else if (distance > 0 && distance <= CATCH__DISTANCE_THRESHOLD) {
            SetMarker(
               location,
                boundary = true,
                mapViewModel
            )
        }
    }
}

// 두 위치 간의 거리를 계산
fun calculateDistance(location1: LatLng, location2: LatLng): Float {
    val results = FloatArray(1)
    Location.distanceBetween(
        location1.latitude, location1.longitude,
        location2.latitude, location2.longitude,
        results
    )
    return results[0]
}



private const val MAX_DISTANCE_THRESHOLD = 150f // 사용자 반경
private const val CATCH__DISTANCE_THRESHOLD = 50f // 잡을수 있는 범위 반경
