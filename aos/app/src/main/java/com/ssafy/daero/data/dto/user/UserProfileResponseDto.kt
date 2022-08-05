package com.ssafy.daero.data.dto.user

data class UserProfileResponseDto(
    val nickname : String,
    val profile_url : String,
    val follower : Int,
    val following : Int,
    val followYn : Char,
    val badge_count : Int,
    val del_yn: Char
)

