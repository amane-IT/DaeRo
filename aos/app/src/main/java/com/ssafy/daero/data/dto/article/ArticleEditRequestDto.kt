package com.ssafy.daero.data.dto.article

data class ArticleEditRequestDto(
    var title: String,
    var tripComment: String,
    var tripExpenses: String,
    var rating: Int,
    var expose: Char,
    var records: MutableList<String>
)
