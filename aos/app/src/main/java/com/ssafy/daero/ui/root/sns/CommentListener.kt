package com.ssafy.daero.ui.root.sns

interface CommentListener {
    fun commentUpdate(content: String, sequence: Int)
    fun reCommentAdd(sequence: Int)
}