package com.ssafy.daero.data.dto.service

data class InquiryResponseDto(
    val title: String,
    val content: String,
    val created_at: String,
    val answer_yn: Character,
    val answer: String,
    val answer_at: String,
    var isShow: Boolean = false
)
