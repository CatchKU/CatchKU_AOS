package com.example.catchku.domain.entity

import com.naver.maps.geometry.LatLng

data class ItemLocation(
    val latLng: LatLng,
    val itemImageResource: Int,
    val itemName: String
)