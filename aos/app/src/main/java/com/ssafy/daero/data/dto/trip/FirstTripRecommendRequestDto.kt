package com.ssafy.daero.data.dto.trip

data class FirstTripRecommendRequestDto(
    val regions : List<Int>,
    val tags: List<Int>
)