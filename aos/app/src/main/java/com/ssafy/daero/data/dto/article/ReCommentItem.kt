package com.ssafy.daero.data.dto.article

data class ReCommentItem(
    val reply_seq: Int,
    val user_seq: Int,
    val nickname: String,
    val profile_url: String,
    val created_at: String,
    val modified: Char,
    val content: String
)
