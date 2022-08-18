package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.EMPTY
import com.ssafy.daero.utils.constant.FAIL
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class TripNextViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val showProgress = MutableLiveData<Boolean>()

    private val _nextTripRecommendResponseDto = MutableLiveData<Int>()
    val nextTripRecommendResponseDto: LiveData<Int>
        get() = _nextTripRecommendResponseDto

    val imageUrl = MutableLiveData<String>()

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
                })
        )
    }

    fun getAroundTrips(placeSeq: Int) {
        addDisposable(
            tripRepository.getAroundTrips(placeSeq)
                .subscribe({
                    _aroundTrips.postValue(it.body())
                }, { _ ->
                })
        )
    }

    fun recommendNextPlace(
        time: Int,
        transportation: String
    ) {
        showProgress.postValue(true)

        addDisposable(
            tripRepository.recommendNextPlace(
                App.prefs.curPlaceSeq,
                time,
                transportation
            ).flatMap { response ->
                if(response.body()!!.place_seq > 0) {
                    imageUrl.postValue(response.body()!!.image_url)
                }
                Single.just(response.body()!!.place_seq).delay(1000, TimeUnit.MILLISECONDS)
            }.subscribe( {
                if(it > 0) {
                    _nextTripRecommendResponseDto.postValue(it)
                } else {
                    nextTripRecommendState.postValue(EMPTY)
                }
                showProgress.postValue(false)
            }, {
                showProgress.postValue(false)
                nextTripRecommendState.postValue(FAIL)
            })
        )
    }

    fun deleteAllTripRecord() {
        addDisposable(
            tripRepository.deleteAllTripStamps()
                .subscribe({
                }, { throwable ->
                })
        )
        addDisposable(
            tripRepository.deleteAllTripFollow()
                .subscribe({

                }, { throwable ->
                })
        )
    }

    fun initNextTripRecommend() {
        _nextTripRecommendResponseDto.value = 0
    }
}