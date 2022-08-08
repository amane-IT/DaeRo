package com.ssafy.daero.data.dto.article

data class ArticleResponseDto(
    val user_seq: Int,
    val nickname: String,
    val profile_url: String,
    val like_yn : Char,
    val title: String,
    val expose : Char,
    val trip_comment: String,
    val trip_expenses: List<Expense>,
    val rating: Int,
    val likes: Int,
    val comments: Int,
    val tags: List<Int>,
    val records: List<Record>
)