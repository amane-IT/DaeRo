package com.ssafy.daero.data.dto.article

data class ArticleHomeItem(
    val article_seq: Int,
    val comments: Int,
    val created_at: String,
    val description: String,
    val end_date: String,
    val likes: Int,
    val nickname: String,
    val profile_url: String,
    val start_date: String,
    val thumbnail_url: String,
    val title: String,
    val user_seq: Int
)