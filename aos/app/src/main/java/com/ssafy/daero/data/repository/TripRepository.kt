package com.ssafy.daero.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import androidx.room.Room
import com.ssafy.daero.data.dto.trip.*
import com.ssafy.daero.data.entity.Notification
import com.ssafy.daero.data.entity.TripFollow
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.local.AppDatabase
import com.ssafy.daero.data.remote.TripApi
import com.ssafy.daero.data.repository.paging.TripAlbumDataSource
import com.ssafy.daero.data.repository.paging.TripMyAlbumDataSource
import com.ssafy.daero.utils.constant.DATABASE
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response

class TripRepository private constructor(context: Context) {
    // Database
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE
    ).build()

    // Trip API
    private val tripApi = RetrofitBuilder.retrofit.create(TripApi::class.java)

    fun getMyJourney(
        user_seq: Int,
        startDate: String,
        endDate: String
    ): Single<Response<List<List<MyJourneyResponseDto>>>> {
        return tripApi.getMyJourney(user_seq, startDate, endDate)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getJourney(
        user_seq: Int,
        startDate: String,
        endDate: String
    ): Single<Response<List<List<MyJourneyResponseDto>>>> {
        return tripApi.getJourney(user_seq, startDate, endDate)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFirstTripRecommend(
        firstTripRecommendRequestDto: FirstTripRecommendRequestDto
    ): Single<Response<FirstTripRecommendResponseDto>> {
        return tripApi.getFirstTripRecommend(firstTripRecommendRequestDto)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTripInformation(placeSeq: Int): Single<Response<TripInformationResponseDto>> {
        return tripApi.getTripInformation(placeSeq)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMyAlbum(): Flowable<PagingData<TripAlbumItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { TripMyAlbumDataSource(tripApi) }
        ).flowable
    }

    fun getAlbum(userSeq: Int): Flowable<PagingData<TripAlbumItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { TripAlbumDataSource(tripApi, userSeq) }
        ).flowable
    }

    fun getPopularTrips(): Single<Response<List<TripPopularResponseDto>>> {
        return tripApi.getPopularTrips()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    // 알림 저장
    fun insertNotification(notification: Notification): Completable {
        return database.notificationDao().insertNotification(notification)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // 알림 받아오기기
    fun getNotifications(): Single<List<Notification>> {
        return database.notificationDao().getNotifications()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // 알림 전체 삭제
    fun deleteAllNotifications(): Completable {
        return database.notificationDao().deleteAllNotifications()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // TripStamp 저장
    fun insertTripStamp(tripStamp: TripStamp): Completable {
        return database.tripStampDao().insertTripStamp(tripStamp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // TripStamp 모두 가져오기
    fun getTripStamps(): Single<List<TripStamp>> {
        return database.tripStampDao().getTripStamps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // 다음 여행지 추천받기
    fun recommendNextPlace(
        placeSeq: Int,
        time: Int,
        transportation: String
    ): Single<Response<NextPlaceRecommendResponseDto>> {
        return tripApi.recommendNextPlace(placeSeq, time, transportation)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    //TripFollow 저장
    fun insertTripFollow(tripFollow: TripFollow): Completable {
        return database.tripFollowDao().insertTripFollow(tripFollow)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // TripFollow 모두 가져오기
    fun getTripFollows(): Single<List<TripFollow>> {
        return database.tripFollowDao().getTripFollows()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        private var instance: TripRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = TripRepository(context)
            }
        }

        fun get(): TripRepository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }

}