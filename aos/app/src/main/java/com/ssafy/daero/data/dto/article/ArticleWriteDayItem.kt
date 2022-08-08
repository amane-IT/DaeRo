package com.ssafy.daero.data.dto.article

import kotlinx.serialization.Serializable

@Serializable
data class ArticleWriteDayItem(
    var datetime: String,
    var dayComment: String,
    var tripStamps: List<ArticleWriteTripStampItem>
)
