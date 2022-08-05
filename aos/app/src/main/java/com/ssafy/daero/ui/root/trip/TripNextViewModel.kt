package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TripNextViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val showProgress = MutableLiveData<Int>()

    private val _nextTripRecommendResponseDto = MutableLiveData<Int>()
    val nextTripRecommendResponseDto: LiveData<Int>
        get() = _nextTripRecommendResponseDto

    var nextTripRecommendState = MutableLiveData<Int>()

    private val _tripStamps = MutableLiveData<List<TripStamp>>()
    val tripStamps: LiveData<List<TripStamp>>
        get() = _tripStamps

    private val _aroundTrips = MutableLiveData<List<TripPopularResponseDto>>()
    val aroundTrips: LiveData<List<TripPopularResponseDto>>
        get() = _aroundTrips

    fun getTripStamps() {
        addDisposable(
            tripRepository.getTripStamps()
                .subscribe({
                    _tripStamps.postValue(it)
                }, { throwable ->
                    Log.d("TripNextVM_DaeRo", throwable.toString())
                })
        )
    }

    fun getAroundTrips(placeSeq: Int) {
        addDisposable(
            tripRepository.getAroundTrips(placeSeq)
                .subscribe({
                    _aroundTrips.postValue(it.body())
                }, { throwable ->
                    Log.d("TripNextVM_DaeRo", "getAroundTrips: ")
                })
        )
    }

    fun recommendNextPlace(
        time: Int,
        transportation: String
    ) {
        showProgress.postValue(SUCCESS)

        // 2.5초 딜레이
        val delay =
            Single.just(1).delay(2500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        // 서버 결과, 2.5초 경과 모두 끝나야 응답 받기
        addDisposable(
            Single.zip(
                delay,
                tripRepository.recommendNextPlace(
                    App.prefs.placeSeq,
                    time,
                    transportation
                ),
                BiFunction { _, response ->
                    response
                }).subscribe(
                { response ->
                    // place_seq 저장
                    _nextTripRecommendResponseDto.postValue(response.body()!!.place_seq)
                    showProgress.postValue(FAIL)
                },
                { throwable ->
                    Log.d("TripNextVM_DaeRo", throwable.toString())
                    showProgress.postValue(FAIL)
                    nextTripRecommendState.postValue(FAIL)
                })
        )
    }

}