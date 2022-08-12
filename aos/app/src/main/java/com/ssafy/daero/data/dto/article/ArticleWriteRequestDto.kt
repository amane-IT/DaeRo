package com.ssafy.daero.data.dto.article

import kotlinx.serialization.Serializable

@Serializable
data class ArticleWriteRequestDto(
    var title: String,
    var tripComment: String,
    var tripExpenses: String,
    var rating: Int,
    var expose: Char,
    var thumbnailIndex: Int,
    var records: List<ArticleWriteDayItem>
)
