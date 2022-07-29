package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.data.dto.trip.FirstTripRecommendResponseDto
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TripViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val showProgress = MutableLiveData<Int>()

    private val _firstTripRecommendResponseDto = MutableLiveData<Int>()
    val firstTripRecommendResponseDto: LiveData<Int>
        get() = _firstTripRecommendResponseDto

    var firstTripRecommendState = MutableLiveData<Int>()

    fun getFirstTripRecommend(firstTripRecommendRequestDto: FirstTripRecommendRequestDto) {
        showProgress.postValue(SUCCESS)

        // 2.5초 딜레이
        val delay =
            Single.just(1).delay(2500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        // 서버 결과, 2.5초 경과 모두 끝나야 응답 받기
        addDisposable(
            Single.zip(
                delay,
                tripRepository.getFirstTripRecommend(firstTripRecommendRequestDto),
                BiFunction { _, response ->
                    response
                }).subscribe(
                { response ->
                    // userSeq 저장
                    _firstTripRecommendResponseDto.postValue(response.body()!!.place_seq)
                    showProgress.postValue(FAIL)
                },
                { throwable ->
                    Log.d("TripVM_DaeRo", throwable.toString())
                    showProgress.postValue(FAIL)
                    firstTripRecommendState.postValue(FAIL)
                })
        )

        /*
        // 임시 코드, 2.5초 후에 placeSeq = 1 발행
        delay.subscribe(
            { response ->
                // userSeq 저장
                //_firstTripRecommendResponseDto.postValue(response.body())
                _firstTripRecommendResponseDto.postValue(
                    1
                )
                showProgress.postValue(FAIL)
            },
            { throwable ->
                Log.d("TripVM_DaeRo", throwable.toString())
                showProgress.postValue(FAIL)
                firstTripRecommendState.postValue(FAIL)
            })

         */
    }

    fun initTripInformation() {
        _firstTripRecommendResponseDto.value = 0
    }
}