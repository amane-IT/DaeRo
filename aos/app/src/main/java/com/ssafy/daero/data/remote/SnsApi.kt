package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.search.UserNameItem
import com.ssafy.daero.data.dto.article.*
import com.ssafy.daero.data.dto.collection.CollectionItem
import com.ssafy.daero.data.dto.common.PagingResponseDto
import com.ssafy.daero.data.dto.search.ArticleMoreItem
import com.ssafy.daero.data.dto.search.SearchArticleResponseDto
import com.ssafy.daero.data.dto.trip.MyJourneyResponseDto
import com.ssafy.daero.data.dto.trip.TripFollowSelectResponseDto
import com.ssafy.daero.data.dto.trip.TripHotResponseDto
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.data.dto.user.FollowResponseDto
import com.ssafy.daero.data.dto.user.UserBlockResponseDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface SnsApi {

    /**
     * 게시글 목록 조회
     */
    @GET("sns")
    fun getArticles(@Query("page") page: Int) : Single<PagingResponseDto<ArticleHomeItem>>

    /**
     * 게시글(여행기록) 상세 조회
     */
    @GET("sns/article/{article_seq}")
    fun article(@Path("article_seq") articleSeq: Int): Single<Response<ArticleResponseDto>>

    /**
     * 게시글 삭제
     */
    @DELETE("sns/article/{article_seq}")
    fun articleDelete(
        @Path("article_seq") articleSeq: Int
    ): Completable

    /**
     * 댓글 조회
     */
    @GET("sns/article/{article_seq}/reply")
    fun commentSelect(@Path("article_seq") articleSeq: Int, @Query("page") page: Int): Single<PagingResponseDto<CommentItem>>

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
    @GET("sns/article/{article_seq}/reply/{reply_seq}")
    fun reCommentSelect(
        @Path("article_seq") articleSeq: Int,
        @Path("reply_seq") replySeq: Int,
        @Query("page") page: Int
    ): Single<PagingResponseDto<ReCommentItem>>

    /**
     * 대댓글 추가
     */
    @POST("sns/article/{article_seq}/reply/{reply_seq}/rereply")
    fun reCommentAdd(
        @Path("article_seq") articleSeq: Int,
        @Path("reply_seq") replySeq: Int,
        @Body commentAddRequestDto: CommentAddRequestDto
    ): Completable

    /**
     * 좋아요 추가
     */
    @POST("sns/like")
    fun likeAdd(
        @Query("user") userSeq: Int,
        @Query("article") articleSeq: Int
    ): Completable

    /**
     * 좋아요 삭제
     */
    @DELETE("sns/like")
    fun likeDelete(
        @Query("user") userSeq: Int,
        @Query("article") articleSeq: Int
    ): Completable

    /**
     * 좋아요 누른 사람 목록
     */
    @GET("sns/article/{article_seq}/likes")
    fun getLikeUsers(
        @Path("article_seq") articleSeq: Int,
        @Query("page") page: Int
    ): Single<PagingResponseDto<LikeItem>>

    /**
     * 게시글 신고하기
     */
    @POST("sns/article/{article_seq}/report")
    fun reportArticle(
        @Path("article_seq") articleSeq: Int,
        @Body reportRequest: ReportRequestDto
    ): Completable

    /**
     * 유저 신고하기
     */
    @POST("sns/user/{user_seq}/report")
    fun reportUser(
        @Path("user_seq") userSeq: Int,
        @Body reportRequest: ReportRequestDto
    ): Completable

    /**
     * 댓글 신고하기
     */
    @POST("sns/reply/{reply_seq}/report")
    fun reportComment(
        @Path("reply_seq") replySeq: Int,
        @Body reportRequest: ReportRequestDto
    ): Completable

    /**
     * 팔로우
     */
    @POST("sns/follow")
    fun follow(
        @Query("follow-user") userSeq: Int
    ): Completable

    /**
     * 언팔로우
     */
    @DELETE("sns/follow")
    fun unFollow(
        @Query("follow-user") userSeq: Int
    ): Completable

    /**
     * 팔로워 목록
     */
    @GET("sns/user/{user_seq}/follower")
    fun follower(
        @Path("user_seq") userSeq: Int,
        @Query("page") page: Int
    ): Single<PagingResponseDto<FollowResponseDto>>

    /**
     * 팔로잉 목록
     */
    @GET("sns/user/{user_seq}/following")
    fun following(
        @Path("user_seq") userSeq: Int,
        @Query("page") page: Int
    ): Single<PagingResponseDto<FollowResponseDto>>

    /**
     * 사용자 이름 검색
     * */
    @GET("sns/search")
    fun searchUserName(
        @Query("user") userNickname: String,
        @Query("page") page: Int
    ): Single<PagingResponseDto<UserNameItem>>

    /**
     * 게시물 검색 목록
     * */
    @GET("sns/search")
    fun searchArticles(
        @Query("article") article: String
    ): Single<Response<SearchArticleResponseDto>>

    /**
     * 내용 검색 목록
     * */
    @GET("sns/search")
    fun searchContentMore(
        @Query("content") content: String,
        @Query("page") page: Int
    ): Single<PagingResponseDto<ArticleMoreItem>>

    /**
     * 여행지 검색 목록
     * */
    @GET("sns/search")
    fun searchPlaceMore(
        @Query("place") place: String,
        @Query("page") page: Int
    ): Single<PagingResponseDto<ArticleMoreItem>>

    /**
     * 따라가기
     */
    @GET("sns/article/{article_seq}/trace")
    fun getTripFollow(
        @Path("article_seq") articleSeq: Int
    ): Single<Response<List<TripFollowSelectResponseDto>>>
    
    /**
     * 컬렉션 목록
     **/
    @GET("sns/collection")
    fun getCollections(
        @Query("page") page: Int
    ): Single<PagingResponseDto<CollectionItem>>

    /**
     * 게시물 숨기기
     */
    @POST("sns/article/{article_seq}/hide")
    fun blockArticle(
        @Path("article_seq") articleSeq: Int
    ): Completable

    /**
     * 차단하기
     */
    @POST("sns/users/{user_seq}/block")
    fun blockAdd(
        @Path("user_seq") userSeq: Int
    ): Completable

    /**
     * 차단해제
     */
    @DELETE("sns/users/{user_seq}/block")
    fun blockDelete(
        @Path("user_seq") userSeq: Int
    ): Completable

    /**
     * 차단 목록
     */
    @GET("sns/users/block")
    fun getBlockUser(): Single<Response<List<UserBlockResponseDto>>>

    /**
     * 댓글 수정
     */
    @PUT("sns/article/{article_seq}/public/close")
    fun articleClose(
        @Path("article_seq") articleSeq: Int
    ): Completable

    /**
     * 댓글 수정
     */
    @PUT("sns/article/{article_seq}/public/open")
    fun articleOpen(
        @Path("article_seq") articleSeq: Int
    ): Completable

    /**
     * 핫한 여행기
     */
    @GET("sns/article/popular")
    fun getHotArticles() : Single<Response<List<TripHotResponseDto>>>

    /**
     * 게시글 수정
     */
    @PUT("sns/article/{article_seq}")
    fun editArticle(@Path("article_seq") articleSeq: Int, @Body articleEditRequestDto: ArticleEditRequestDto) : Single<Response<Unit>>
}
