package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.article.ArticleResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SnsApi {

    /**
     * 게시글(여행기록) 상세 조회
     */
    @GET("sns/article/{article_seq}")
    fun article(@Path("article_seq") articleSeq: String): Single<Response<ArticleResponseDto>>
}