package com.example.catchku.domain.entity

import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.flow.MutableStateFlow

data class MarkerLocation(
    val latLng: LatLng,
    val KuId: Int,
    val kuName: String,
    var isVisible: MutableStateFlow<Boolean> = MutableStateFlow(true)
)
