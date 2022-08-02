package com.ssafy.daero.data.dto.trip

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto (
    var rainType: String,      // 강수 형태
    var humidity: String,      // 습도
    var sky: String,           // 하능 상태
    var temp: String,          // 기온
    var fcstTime: String,      // 예보시각
)