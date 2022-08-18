package com.ssafy.daero.data.dto.search

data class ArticleMoreItem(
    val end_date: String,
    val profile_url: String,
    val user_seq: Int,
    val created_at: String,
    val description: String,
    val thumbnail_url: String,
    val title: String,
    val replies: Int,
    val nickname: String,
    val article_seq: Int,
    var like_yn: Char,
    val start_date: String,
    val likes: Int
)