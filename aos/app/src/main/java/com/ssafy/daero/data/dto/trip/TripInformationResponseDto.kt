package com.ssafy.daero.data.dto.trip

data class TripInformationResponseDto(
    val address: String,
    val description: String,
    val image_url: String,
    val latitude: Double,
    val longitude: Double,
    val place_name: String,
    val place_seq: Int
)