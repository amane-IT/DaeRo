package com.ssafy.daero.data.dto.trip

data class FirstTripRecommendResponseDto(
    val address: String,
    val description: String,
    val image_url: String,
    val latitude: String,
    val longitude: String,
    val place_name: String,
    val place_seq: String
)