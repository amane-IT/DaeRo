package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.data.dto.trip.TripInformationResponseDto
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TripInformationViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    private val _tripInformation = MutableLiveData<TripInformationResponseDto>()
    val tripInformation: LiveData<TripInformationResponseDto>
        get() = _tripInformation

    var tripInformationState = MutableLiveData<Int>()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val placeSeq = MutableLiveData<Int>()
    val imageUrl = MutableLiveData<String>()

    // 1초 딜레이
    private val delay =
        Single.just(1).delay(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    /**
     * 여행지 정보
     */
    fun getTripInformation(placeSeq: Int) {
        addDisposable(
            tripRepository.getTripInformation(placeSeq)
                .subscribe(
                    { response ->
                        _tripInformation.postValue(
                            response.body()
                        )
                        _showProgress.postValue(false)
                    },
                    throwableBlock
                )
        )
    }

    /**
     * 첫 여행지 다시 추천
     */
    fun getReFirstTripRecommend(firstTripRecommendRequestDto: FirstTripRecommendRequestDto) {
        _showProgress.postValue(true)

        // 첫 여행지 다시 추천
        addDisposable(
            tripRepository.getFirstTripRecommend(firstTripRecommendRequestDto)
                .flatMap { response ->
                    imageUrl.postValue(response.body()!!.image_url)
                    placeSeq.postValue(response.body()!!.place_seq)
                    Single.just(response.body()!!.place_seq).delay(1000, TimeUnit.MILLISECONDS)
                }
                .flatMap { placeSeq ->
                    tripRepository.getTripInformation(placeSeq)
                }.subscribe(
                    { response ->
                        _tripInformation.postValue(
                            response.body()
                        )
                        _showProgress.postValue(false)
                    },
                    throwableBlock
                )
        )
    }

    /**
     * 다음 여행지 다시 추천
     */
    fun getNextTripRecommend() {
        _showProgress.postValue(true)

        addDisposable(
            tripRepository.recommendNextPlace(
                App.prefs.curPlaceSeq,
                App.prefs.tripTime,
                App.prefs.tripTransportation
            ).flatMap { response ->
                imageUrl.postValue(response.body()!!.image_url)
                placeSeq.postValue(response.body()!!.place_seq)
                Single.just(response.body()!!.place_seq).delay(1000, TimeUnit.MILLISECONDS)
            }.flatMap { placeSeq ->
                tripRepository.getTripInformation(placeSeq)
            }.subscribe(
                { response ->
                    _tripInformation.postValue(
                        response.body()
                    )
                    _showProgress.postValue(false)
                }, throwableBlock
            )
        )
    }

    private val throwableBlock: (Throwable) -> Unit = { _ ->
        _showProgress.postValue(false)
        tripInformationState.postValue(FAIL)
    }
}