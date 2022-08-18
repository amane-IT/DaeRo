package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.data.dto.trip.FirstTripRecommendResponseDto
import com.ssafy.daero.data.dto.trip.TripHotResponseDto
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class TripViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()
    private val snsRepository = SnsRepository.get()

    val showProgress = MutableLiveData<Int>()
    val imageUrl = MutableLiveData<String>()

    private val _firstTripRecommendResponseDto = MutableLiveData<FirstTripRecommendResponseDto>()
    val firstTripRecommendResponseDto: LiveData<FirstTripRecommendResponseDto>
        get() = _firstTripRecommendResponseDto

    private val _popularTrip = MutableLiveData<List<TripPopularResponseDto>>()
    val popularTrip: LiveData<List<TripPopularResponseDto>>
        get() = _popularTrip


    private val _hotArticles = MutableLiveData<List<TripHotResponseDto>>()
    val hotArticle: LiveData<List<TripHotResponseDto>>
        get() = _hotArticles

    var firstTripRecommendState = MutableLiveData<Int>()

    fun getFirstTripRecommend(firstTripRecommendRequestDto: FirstTripRecommendRequestDto) {
        showProgress.postValue(SUCCESS)

        addDisposable(
            tripRepository.getFirstTripRecommend(firstTripRecommendRequestDto)
                .flatMap { response ->
                    imageUrl.postValue(response.body()!!.image_url)
                    Single.just(response).delay(1000, TimeUnit.MILLISECONDS)
                }.subscribe(
                    { response ->
                        // userSeq 저장
                        _firstTripRecommendResponseDto.postValue(response.body())
                        showProgress.postValue(FAIL)
                    },
                    { throwable ->
                        showProgress.postValue(FAIL)
                        firstTripRecommendState.postValue(FAIL)
                    }
                )
        )
    }

    fun getPopularTrips() {
        addDisposable(
            tripRepository.getPopularTrips()
                .subscribe({
                    _popularTrip.postValue(it.body())
                }, { _ ->
                })
        )
    }

    fun getHotArticle() {
        addDisposable(
            snsRepository.getHotArticles()
                .subscribe({
                    _hotArticles.postValue(it.body())
                }, { _ ->
                })
        )
    }

    fun initTripInformation() {
        _firstTripRecommendResponseDto.value = FirstTripRecommendResponseDto(0, "")
    }
}