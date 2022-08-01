package com.ssafy.daero.data.dto.article

data class CommentItem(
    val reply_seq: Int,
    val nickname: String,
    val user_seq: Int,
    val profile_url: String,
    val created_at: String,
    val modified: Char,
    val content: String,
    val rereply_count: Int
)
