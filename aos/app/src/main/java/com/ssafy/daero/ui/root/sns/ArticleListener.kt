package com.ssafy.daero.ui.root.sns

import retrofit2.http.Path

interface ArticleListener {
    fun articleDelete(articleSeq: Int)
    fun blockAdd(userSeq: Int)
}