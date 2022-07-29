package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.trip.*
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface TripApi {
    /**
     * 내 여정 조회
     */
    @GET("trips/my/{user_seq}/journey")
    fun getMyJourney(
        @Path("user_seq") user_seq: Int,
        @Query("start-date") startDate: String,
        @Query("end-date") endDate: String
    ): Single<Response<List<List<MyJourneyResponseDto>>>>

    /**
     * 내 여행 앨범 조회
     */
    @GET("trips/my/{user_seq}/album")
    fun getMyAlbum(
        @Path("user_seq") userSeq: Int,
        @Query("page") page: Int
    ) : Single<TripAlbumResponseDto>

    /**
     * 첫 여행지 추천
     */
    @POST("trips/recommend")
    fun getFirstTripRecommend(
        @Body firstTripRecommendRequestDto: FirstTripRecommendRequestDto
    ): Single<Response<FirstTripRecommendResponseDto>>

    /**
     * 여행지 정보
     */
    @GET("trips/info/{trip_places_seq}")
    fun getTripInformation(@Path("trip_places_seq") placeSeq: Int): Single<Response<TripInformationResponseDto>>
}