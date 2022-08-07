package com.ssafy.daero.data.dto.search

data class ArticleMoreItem(
//    val article_seq: Int,
//    val nickname: String,
//    val user_seq: Int,
//    val profile_url: String,
//    val created_at: String,
//    val thumbnail_url: String,
//    val description: String,
//    val title: String,
//    val start_date: String,
//    val end_date: String,
//    val likes: Int,
//    val comments: Int

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
    val like_yn: Char,
    val start_date: String,
    val likes: Int
)