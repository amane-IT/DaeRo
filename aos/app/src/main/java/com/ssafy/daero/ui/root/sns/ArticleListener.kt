package com.ssafy.daero.ui.root.sns

import retrofit2.http.Path

interface ArticleListener {
    fun articleDelete(articleSeq: Int)
    fun blockArticle(userSeq: Int)
    fun setPublic()
    fun articleOpen(articleSeq: Int)
    fun articleClose(articleSeq: Int)
}