package com.ssafy.daero.data.dto.search

data class SearchArticleResponseDto(
    val content: List<ArticleItem>,
    val place: List<ArticleItem>
)
