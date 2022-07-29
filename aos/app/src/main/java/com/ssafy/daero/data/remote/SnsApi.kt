package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.article.ArticleResponseDto
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.data.dto.article.CommentResponseDto
import com.ssafy.daero.data.dto.article.ReCommentResponseDto
import com.ssafy.daero.data.dto.login.JwtLoginResponseDto
import com.ssafy.daero.data.dto.user.ProfileEditRequestDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface SnsApi {

    /**
     * 게시글(여행기록) 상세 조회
     */
    @GET("sns/article/{article_seq}")
    fun article(@Path("article_seq") articleSeq: String): Single<Response<ArticleResponseDto>>

    /**
     * 댓글 조회
     */
    @GET("sns/article/{article_seq}/reply?page={page}")
    fun commentSelect(@Path("article_seq") articleSeq: Int, @Path("page") page: Int): Single<Response<List<CommentResponseDto>>>

    /**
     * 댓글 추가
     */
    @POST("sns/article/{article_seq}/reply")
    fun commentAdd(@Path("article_seq") articleSeq: Int, @Body commentAddRequestDto: CommentAddRequestDto): Completable

    /**
     * 댓글 수정
     */
    @PUT("sns/reply/{reply_seq}")
    fun commentUpdate(
        @Path("reply_seq") replySeq: Int,
        @Body commentAddRequestDto: CommentAddRequestDto
    ): Completable

    /**
     * 댓글 삭제
     */
    @DELETE("sns/reply/{reply_seq}")
    fun commentDelete(
        @Path("reply_seq") replySeq: Int
    ): Completable

    /**
     * 대댓글 조회
     */
    @GET("sns/article/{article_seq}/reply/{reply_seq}?page={page}")
    fun reCommentSelect(
        @Path("article_seq") articleSeq: Int,
        @Path("reply_seq") replySeq: Int,
        @Path("page") page: Int
    ): Single<Response<List<ReCommentResponseDto>>>

    /**
     * 대댓글 추가
     */
    @POST("sns/article/{article_seq}/reply/{reply_seq}/rereply")
    fun reCommentAdd(
        @Path("article_seq") articleSeq: Int,
        @Path("reply_seq") replySeq: Int,
        @Body commentAddRequestDto: CommentAddRequestDto
    ): Completable
}