package com.example.catchku.domain.entity

import com.naver.maps.geometry.LatLng

data class MarkerLocation(
    val latLng: LatLng,
    val KuId: Int,
    val kuName: String
)