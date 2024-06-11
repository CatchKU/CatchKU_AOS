package com.example.catchku.screen.map

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.catchku.domain.entity.ItemLocation
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(navController: NavHostController, mapViewModel: MapViewModel) {
    val lifecycleOwner = LocalLifecycleOwner
    val uiCatchKuState by mapViewModel.postKuCatchState
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    val uiObtainItemState by mapViewModel.postUserObtainItemState
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    mapViewModel.getUserId()

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
                DrawKuMarker(currLocation = it, mapViewModel)
                DrawUserMarker(currLocation = it, mapViewModel)
                DrawItemMarker(currLocation = it, mapViewModel)
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
    val activity = context as? Activity
    val isVisible by markerLocation.isVisible.collectAsState()

    if (isVisible) {
        Marker(
            state = MarkerState(
                position = LatLng(
                    markerLocation.latLng.latitude,
                    markerLocation.latLng.longitude
                )
            ),
            icon = OverlayImage.fromResource(markerLocation.KuId),
            width = 30.dp,
            height = 30.dp,
            onClick = {
                if (boundary) {
//                    activity?.let { activity ->
//                        navigateToUnityActivity(activity)
//                    }
                    mapViewModel.postKuCatch(mapViewModel.initUserId.value, markerLocation.kuName)
                    mapViewModel.hideMarkerFor10Seconds(markerLocation)
                    true
                } else {
                    Toast.makeText(context, "It's too far!", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        )
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SetItemMarker(
    itemLocation: ItemLocation,
    boundary: Boolean,
    mapViewModel: MapViewModel
) {
    val context = LocalContext.current
    Marker(
        state = MarkerState(
            position = LatLng(
                itemLocation.latLng.latitude,
                itemLocation.latLng.longitude
            )
        ),
        icon = OverlayImage.fromResource(itemLocation.itemImageResource),
        width = 30.dp,
        height = 30.dp,
        onClick = {
            if (boundary) {
                mapViewModel.postUserObtainItem(
                    mapViewModel.initUserId.value,
                    itemLocation.itemName
                )
                Toast.makeText(context, "Acquire Item!", Toast.LENGTH_SHORT).show()
                true
            } else {
                Toast.makeText(context, "It's too far", Toast.LENGTH_SHORT).show()
                false
            }
        }
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DrawUserMarker(currLocation: LatLng, mapViewModel: MapViewModel) {

    // 사용자의 현재 위치
    CircleOverlay(
        center = LatLng(currLocation.latitude, currLocation.longitude),
        Color.Red.copy(alpha = 0.3F),
        mapViewModel.maxDistanceThreshold.value.toDouble()
    )

    CircleOverlay(
        center = LatLng(currLocation.latitude, currLocation.longitude),
        color = Color(0xFFCF4A4A),
        mapViewModel.catchDistanceThreshold.value.toDouble()
    )
}

@Composable
fun DrawKuMarker(currLocation: LatLng, mapViewModel: MapViewModel) {
    // 사용자의 현재 위치
    val userLocation = currLocation

    val markerLocation = rememberSaveable {
        listOf(
            MarkerLocation(LatLng(37.5431505, 127.0751552), R.drawable.ku, "쿠"), // 행정관
            MarkerLocation(LatLng(37.5442615, 127.0760717), R.drawable.computer_ku, "공대 쿠"), // 경영관
            MarkerLocation(LatLng(37.5441682, 127.0753535), R.drawable.crying_catched_ku, "우는 쿠"), // 상허연구관
            MarkerLocation(LatLng(37.5439837, 127.0742108), R.drawable.diving_ku, "다이빙 쿠"), // 교육과학관
            MarkerLocation(LatLng(37.542845, 127.0729332), R.drawable.ku, "쿠"),  // 예술문화관
            MarkerLocation(LatLng(37.5426356, 127.074649), R.drawable.computer_ku, "공대 쿠"),  // 언어교육원
            MarkerLocation(LatLng(37.5423945, 127.0756472), R.drawable.crying_catched_ku, "우는 쿠"), // 박물관
            MarkerLocation(LatLng(37.5419017, 127.0749445), R.drawable.diving_ku, "다이빙 쿠"), // 법학관
            MarkerLocation(LatLng(37.5419226, 127.0737408), R.drawable.ku, "쿠"), // 상허기념도서관
            MarkerLocation(LatLng(37.5415596, 127.0721872), R.drawable.computer_ku, "공대 쿠"), // 의생명과학연구관
            MarkerLocation(LatLng(37.5407426, 127.0735979), R.drawable.crying_catched_ku, "우는 쿠"), // 생명과학관
            MarkerLocation(LatLng(37.5403664, 127.0743614), R.drawable.diving_ku, "다이빙 쿠"), // 동물생명과학관
            MarkerLocation(LatLng(37.5402342, 127.0735998), R.drawable.ku, "쿠"), // 입학정보관
            MarkerLocation(LatLng(37.5396663, 127.0732309), R.drawable.computer_ku, "공대 쿠"), // 산학협동관
            MarkerLocation(LatLng(37.5390954, 127.0747386), R.drawable.crying_catched_ku, "우는 쿠"), // 수의학관
            MarkerLocation(LatLng(37.5435659, 127.0772119), R.drawable.diving_ku, "다이빙 쿠"), // 새천년관
            MarkerLocation(LatLng(37.5434839, 127.0785437), R.drawable.ku, "쿠"), // 건축관
            MarkerLocation(LatLng(37.5433009, 127.0782828), R.drawable.computer_ku, "공대 쿠"), // 해봉부동산학과
            MarkerLocation(LatLng(37.5424065, 127.0786945), R.drawable.crying_catched_ku, "우는 쿠"), // 인문학관
            MarkerLocation(LatLng(37.5418772, 127.0782087), R.drawable.diving_ku, "다이빙 쿠"), // 학생회관
            MarkerLocation(LatLng(37.541635, 127.0787904), R.drawable.ku, "쿠"),  // 공학관
            MarkerLocation(LatLng(37.5405464, 127.0794723), R.drawable.computer_ku, "공대 쿠"), // 신공학관
            MarkerLocation(LatLng(37.5414841, 127.0804325), R.drawable.crying_catched_ku, "우는 쿠"), // 과학관
            MarkerLocation(LatLng(37.5407625, 127.0793428), R.drawable.diving_ku, "다이빙 쿠"), // 창의관
            MarkerLocation(LatLng(37.5397343, 127.0772939), R.drawable.ku, "쿠"), // KU기술혁신관
            MarkerLocation(LatLng(37.5391834, 127.0780082), R.drawable.computer_ku, "공대 쿠"), // 쿨하우스
            MarkerLocation(LatLng(37.5404895, 127.0719454), R.drawable.crying_catched_ku, "우는 쿠") // 건국대학교병원
        )
    }

    // 사용자 반경 내 쿠만 표시
    markerLocation.forEach { location ->
        val distance =
            calculateDistance(
                location.latLng,
                userLocation
            )
        val isVisible by location.isVisible.collectAsState()
        if(!isVisible){
           delayShow(location)
        }
        if (distance <= mapViewModel.maxDistanceThreshold.value && distance > mapViewModel.catchDistanceThreshold.value) {
            SetMarker(
                location,
                boundary = false,
                mapViewModel
            )
        } else if (distance > 0 && distance <= mapViewModel.catchDistanceThreshold.value) {
            SetMarker(
                location,
                boundary = true,
                mapViewModel
            )
        }
    }
}

@Composable
fun DrawItemMarker(currLocation: LatLng, mapViewModel: MapViewModel) {
    // 사용자의 현재 위치
    val userLocation = currLocation

    val markerLocations: List<ItemLocation> = listOf(

        ItemLocation(
            LatLng(37.5431505 - 0.0002, 127.0751552+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 행정관
        ItemLocation(
            LatLng(37.5442615 - 0.0002, 127.0760717+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 경영관
        ItemLocation(
            LatLng(37.5441682 - 0.0002, 127.0753535+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 상허연구관
        ItemLocation(
            LatLng(37.5439837 - 0.0002, 127.0742108+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 교육과학관
        ItemLocation(
            LatLng(37.5428450 - 0.0002, 127.0729332+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ),  // 예술문화관
        ItemLocation(
            LatLng(37.5426356 - 0.0002, 127.074649+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ),  // 언어교육원
        ItemLocation(
            LatLng(37.5423945 - 0.0002, 127.0756472+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 박물관
        ItemLocation(
            LatLng(37.5419017 - 0.0002, 127.0749445+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 법학관
        ItemLocation(
            LatLng(37.5419226 - 0.0002, 127.0737408+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 상허기념도서관
        ItemLocation(
            LatLng(37.5415596 - 0.0002, 127.0721872+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 의생명과학연구관
        ItemLocation(
            LatLng(37.5407426 - 0.0002, 127.0735979+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 생명과학관
        ItemLocation(
            LatLng(37.5403664 - 0.0002, 127.0743614+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 동물생명과학관
        ItemLocation(
            LatLng(37.5402342 - 0.0002, 127.0735998+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 입학정보관
        ItemLocation(
            LatLng(37.5396663 - 0.0002, 127.0732309+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 산학협동관
        ItemLocation(
            LatLng(37.5390954 - 0.0002, 127.0747386+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 수의학관
        ItemLocation(
            LatLng(37.5435659 - 0.0002, 127.0772119+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 새천년관
        ItemLocation(
            LatLng(37.5434839 - 0.0002, 127.0785437+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 건축관
        ItemLocation(
            LatLng(37.5433009 - 0.0002, 127.0782828+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 해봉부동산학과
        ItemLocation(
            LatLng(37.5424065 - 0.0002, 127.0786945+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 인문학관
        ItemLocation(
            LatLng(37.5418772 - 0.0002, 127.0782087+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 학생회관
        ItemLocation(
            LatLng(37.5416350 - 0.0002, 127.0787904+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ),  // 공학관
        ItemLocation(
            LatLng(37.5405464 - 0.0002, 127.0794723+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 신공학관
        ItemLocation(
            LatLng(37.5414841 - 0.0002, 127.0804325+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 과학관
        ItemLocation(
            LatLng(37.5407625 - 0.0002, 127.0793428+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 창의관
        ItemLocation(
            LatLng(37.5397343 - 0.0002, 127.0772939+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // KU기술혁신관
        ItemLocation(
            LatLng(37.5391834 - 0.0002, 127.0780082+0.0001), R.drawable.img_mapscreen_item2,
            "조금 더 길어진 밧줄"
        ), // 쿨하우스
        ItemLocation(
            LatLng(37.5404895 - 0.0002, 127.0719454+0.0001), R.drawable.img_mapscreen_item1,
            "쿠 레이더"
        ), // 건국대학교병원
    )

    // 사용자 반경 내 아이템만 표시
    markerLocations.forEach { location ->
        val distance =
            calculateDistance(
                location.latLng,
                userLocation
            )
        if (distance <= mapViewModel.maxDistanceThreshold.value && distance > mapViewModel.catchDistanceThreshold.value) {
            SetItemMarker(
                location,
                boundary = false,
                mapViewModel
            )
        } else if (distance > 0 && distance <= mapViewModel.catchDistanceThreshold.value) {
            SetItemMarker(
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

 fun delayShow(location : MarkerLocation){
    CoroutineScope(Dispatchers.Main).launch{
        delay(10000)
        location.isVisible.value = true
    }
}



