package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.data.dto.trip.TripInformationResponseDto
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.tripInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
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
                    { throwable ->
                        Log.d("TripInfoVM_DaeRo", throwable.toString())
                        tripInformationState.postValue(FAIL)
                    }
                )
        )

        // 임시
        //_tripInformation.postValue(tripInfo)
    }

    /**
     * 첫 여행지 다시 추천
     */
    fun getReFirstTripRecommend(firstTripRecommendRequestDto: FirstTripRecommendRequestDto) {
        _showProgress.postValue(true)

        addDisposable(
            // 첫 여행지 다시 추천
            tripRepository.getFirstTripRecommend(firstTripRecommendRequestDto)
                .subscribe(
                    { response ->
                        // 추천받은 placeSeq 로 여행지 정보 요청
                        tripRepository.getTripInformation(response.body()!!.place_seq)
                            .subscribe(
                                { response ->
                                    _tripInformation.postValue(
                                        response.body()
                                    )
                                    _showProgress.postValue(false)
                                },
                                { throwable ->
                                    Log.d("TripInfoVM_DaeRo", throwable.toString())
                                    _showProgress.postValue(false)
                                    tripInformationState.postValue(FAIL)
                                }
                            )
                    },
                    { throwable ->
                        Log.d("TripInfoVM_DaeRo", throwable.toString())
                        _showProgress.postValue(false)
                        tripInformationState.postValue(FAIL)
                    })
        )
    }
}