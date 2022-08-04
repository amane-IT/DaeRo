package com.ssafy.daero.data.dto.common

data class PagingResponseDto<T>(
    val total_page : Int,
    val page: Int,
    val results: List<T>
)
