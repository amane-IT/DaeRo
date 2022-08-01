package com.ssafy.daero.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import androidx.room.Room
import com.ssafy.daero.data.dto.trip.*
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.local.AppDatabase
import com.ssafy.daero.data.remote.TripApi
import com.ssafy.daero.data.repository.paging.TripAlbumDataSource
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
            pagingSourceFactory = { TripAlbumDataSource(tripApi) }
        ).flowable
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