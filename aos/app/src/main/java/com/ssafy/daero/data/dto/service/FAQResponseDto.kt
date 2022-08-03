package com.ssafy.daero.data.dto.service

data class FAQResponseDto(
    val title: String,
    val content: String,
    val created_at: String,
    var isShow: Boolean = false
)
