package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.EMPTY
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class TripNextViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val showProgress = MutableLiveData<Boolean>()

    private val _nextTripRecommendResponseDto = MutableLiveData<Int>()
    val nextTripRecommendResponseDto: LiveData<Int>
        get() = _nextTripRecommendResponseDto

    val imageUrl = MutableLiveData<String>()

    private val _tripList = MutableLiveData<List<TripStamp>>()
    val tripList: LiveData<List<TripStamp>>
        get() = _tripList

    private val _nearByPlaces = MutableLiveData<List<TripPopularResponseDto>>()
    val nearByPlaces: LiveData<List<TripPopularResponseDto>>
        get() = _nearByPlaces

    var nextTripRecommendState = MutableLiveData<Int>()
    var tripListState = MutableLiveData<Int>()

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
                    Log.d("TripNextVM_DaeRo", throwable.toString())
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
            ).subscribe({ response ->
                // place_seq 저장
                if(response.body()!!.place_seq == 0) {
                    nextTripRecommendState.postValue(EMPTY)
                    imageUrl.postValue(response.body()!!.image_url)
                } else {
                    _nextTripRecommendResponseDto.postValue(response.body()!!.place_seq)
                }
                showProgress.postValue(false)
            }, { throwable ->
                Log.d("TripNextVM_DaeRo", throwable.toString())
                showProgress.postValue(false)
                nextTripRecommendState.postValue(FAIL)
            })
        )
    }

    fun initNextTripRecommend() {
        _nextTripRecommendResponseDto.value = 0
    }
}