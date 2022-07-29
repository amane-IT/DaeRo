package com.ssafy.daero.data.dto.login

data class LoginResponseDto(
    val user_seq: Int,
    val jwt: String,
    val user_nickname: String
)