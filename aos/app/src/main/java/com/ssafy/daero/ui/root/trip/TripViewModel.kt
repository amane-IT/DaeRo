package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.data.dto.trip.FirstTripRecommendResponseDto
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TripViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _firstTripRecommendResponseDto = MutableLiveData<FirstTripRecommendResponseDto>()
    val firstTripRecommendResponseDto: LiveData<FirstTripRecommendResponseDto>
        get() = _firstTripRecommendResponseDto

    var firstTripRecommendState = MutableLiveData<Int>()

    fun getFirstTripRecommend(firstTripRecommendRequestDto: FirstTripRecommendRequestDto) {
        _showProgress.postValue(true)

        // 2.5초 딜레이
        val delay =
            Single.just(1).delay(2500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        // 서버 결과, 2.5초 경과 모두 끝나야 응답 받기
        Single.zip(
            delay,
            tripRepository.getFirstTripRecommend(firstTripRecommendRequestDto),
            BiFunction { _, response ->
                response
            }).subscribe(
            { response ->
                // userSeq 저장
                _firstTripRecommendResponseDto.postValue(response.body())
                _showProgress.postValue(false)
            },
            { throwable ->
                Log.d("TripVM_DaeRo", throwable.toString())
                _showProgress.postValue(false)
                firstTripRecommendState.postValue(FAIL)
            })
    }
}