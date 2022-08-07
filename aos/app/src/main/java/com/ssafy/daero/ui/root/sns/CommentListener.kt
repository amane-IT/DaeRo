package com.ssafy.daero.ui.root.sns

import com.ssafy.daero.ui.adapter.sns.ReCommentAdapter

interface CommentListener {
    fun commentUpdate(content: String, sequence: Int)
    fun commentDelete(sequence: Int)
    fun reCommentAdd(sequence: Int)
    fun reCommentSelect(articleSeq: Int, replySeq: Int, reCommentAdapter: ReCommentAdapter): ReCommentAdapter
    fun blockAdd(userSeq: Int)
}