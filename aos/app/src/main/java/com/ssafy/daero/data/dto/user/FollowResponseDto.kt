package com.ssafy.daero.data.dto.user

data class FollowResponseDto(
    val profile_url: String,
    val nickname: String,
    val user_seq: Int,
    val follow_yn: Char
)
