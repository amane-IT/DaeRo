package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.service.NoticeResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {
    /**
     * 공지사항 조회
     */
    @GET("service/notice")
    fun getNotices() : Single<Response<List<NoticeResponseDto>>>
}