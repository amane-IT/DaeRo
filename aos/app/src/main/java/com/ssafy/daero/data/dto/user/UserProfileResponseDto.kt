package com.ssafy.daero.data.dto.user

data class UserProfileResponseDto(
    val nickname : String,
    val profile_url : String,
    val follower : Int,
    val following : Int,
    val follow_yn : Char,
    val badge_count : Int
)

