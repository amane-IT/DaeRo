package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.service.FAQResponseDto
import com.ssafy.daero.data.dto.service.InquiryResponseDto
import com.ssafy.daero.data.dto.service.NoticeResponseDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApi {
    /**
     * 공지사항 조회
     */
    @GET("service/notice")
    fun getNotices() : Single<Response<List<NoticeResponseDto>>>

    @GET("service/faq")
    fun getFaqs() : Single<Response<List<FAQResponseDto>>>

    /**
     * 1:1 문의 목록 조회
     */
    @GET("service/inquiry")
    fun getInquiry() : Single<Response<List<InquiryResponseDto>>>

    /**
     * 문의하기
     */
    @POST("sns/inquiry")
    fun insertInquiry(
        @Query("follow-user") userSeq: Int
    ): Completable
}