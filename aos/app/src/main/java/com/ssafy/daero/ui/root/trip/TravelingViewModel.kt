package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.data.dto.trip.TripInformationResponseDto
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TravelingViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val responseState = MutableLiveData<Int>()

    private val _tripStamps = MutableLiveData<List<TripStamp>>()
    val tripStamps: LiveData<List<TripStamp>>
        get() = _tripStamps

    private val _tripInformation = MutableLiveData<TripInformationResponseDto>()
    val tripInformation: LiveData<TripInformationResponseDto>
        get() = _tripInformation

    var tripInformationState = MutableLiveData<Int>()

    var placeSeq = MutableLiveData<Int>()
    var imageUrl = MutableLiveData<String>()
    var tripRecommendState = MutableLiveData<Int>()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    // 1초 딜레이
    private val delay =
        Single.just(1).delay(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTripStamps() {
        addDisposable(
            tripRepository.getTripStamps()
                .subscribe({
                    _tripStamps.postValue(it)
                }, { throwable ->
                    responseState.postValue(FAIL)
                })
        )
    }

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
                    },
                    { _ ->
                        tripInformationState.postValue(FAIL)
                    }
                )
        )
    }

    fun deleteAllTripRecord() {
        addDisposable(
            tripRepository.deleteAllTripStamps()
                .subscribe({

                }, { _ ->
                })
        )
        addDisposable(
            tripRepository.deleteAllTripFollow()
                .subscribe({

                }, { _ ->
                })
        )
    }

    fun getFirstTripRecommend(firstTripRecommend: FirstTripRecommendRequestDto) {
        _showProgress.postValue(true)

        addDisposable(
            tripRepository.getFirstTripRecommend(firstTripRecommend)
                .flatMap { response ->
                    if (response.body()!!.place_seq != 0) {
                        imageUrl.postValue(response.body()!!.image_url)
                    }
                    Single.just(response.body()!!.place_seq).delay(1000, TimeUnit.MILLISECONDS)
                }.subscribe(
                    { place_seq ->
                        if (place_seq > 0) {
                            placeSeq.postValue(place_seq)
                        } else {
                            tripRecommendState.postValue(FAIL)
                        }
                        _showProgress.postValue(false)
                    }, { _ ->
                        tripRecommendState.postValue(FAIL)
                        _showProgress.postValue(false)
                    }
                )
        )
    }

    fun recommendNextPlace(time: Int, transportation: String) {
        _showProgress.postValue(true)

        addDisposable(
            tripRepository.recommendNextPlace(
                App.prefs.curPlaceSeq,
                time, transportation
            ).flatMap { response ->
                if (response.body()!!.place_seq != 0) {
                    imageUrl.postValue(response.body()!!.image_url)
                }
                Single.just(response.body()!!.place_seq).delay(1000, TimeUnit.MILLISECONDS)
            }.subscribe(
                { place_seq ->
                    if (place_seq > 0) {
                        placeSeq.postValue(place_seq)
                    } else {
                        tripRecommendState.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { _ ->
                    tripRecommendState.postValue(FAIL)
                    _showProgress.postValue(false)
                }
            )
        )
    }
}