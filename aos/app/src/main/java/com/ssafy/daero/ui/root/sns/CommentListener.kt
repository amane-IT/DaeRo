package com.ssafy.daero.ui.root.sns

import com.ssafy.daero.data.dto.article.ReCommentResponseDto

interface CommentListener {
    fun commentUpdate(content: String, sequence: Int)
    fun commentDelete(sequence: Int)
    fun reCommentAdd(sequence: Int)
    fun reCommentSelect(articleSeq:Int, replySeq:Int, page:Int): List<ReCommentResponseDto>
}