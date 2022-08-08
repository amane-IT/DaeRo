package com.ssafy.daero.data.dto.article

import kotlinx.serialization.Serializable

@Serializable
data class ArticleWriteTripStampItem(
    var satisfaction: Char,
    var tripPlaceSeq: Int
)