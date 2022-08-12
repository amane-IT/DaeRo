package com.ssafy.daero.ui.root.sns

import retrofit2.http.Path

interface ArticleListener {
    fun articleDelete(articleSeq: Int)
    fun blockArticle(articleSeq: Int, position: Int = 0)
    fun setPublic()
    fun articleOpen(articleSeq: Int)
    fun articleClose(articleSeq: Int)
}