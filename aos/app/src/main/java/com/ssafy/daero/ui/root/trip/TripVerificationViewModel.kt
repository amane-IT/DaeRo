package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.TripInformationResponseDto
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class TripVerificationViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val responseState = MutableLiveData<Int>()
    var articleTripStampData = listOf<TripStamp>()

    private val _tripStamps = MutableLiveData<List<TripStamp>>()
    val tripStamps : LiveData<List<TripStamp>>
        get() = _tripStamps

    private val _tripInformation = MutableLiveData<TripInformationResponseDto>()
    val tripInformation: LiveData<TripInformationResponseDto>
        get() = _tripInformation

    var tripInformationState = MutableLiveData<Int>()


    fun getTripStamps() {
        addDisposable(
            tripRepository.getTripStamps()
                .subscribe({
                    _tripStamps.postValue(it)
                }, { throwable ->
                    Log.d("TravelingVM_DaeRo", throwable.toString())
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
                    { throwable ->
                        Log.d("TripInfoVM_DaeRo", throwable.toString())
                        tripInformationState.postValue(FAIL)
                    }
                )
        )
    }

    fun deleteTripStamps() {
        addDisposable(
            tripRepository.deleteAllTripStamps()
                .subscribe({

                }, { throwable->
                    Log.d("TripInfoVM_DaeRo", throwable.toString())
                })
        )
    }

    fun insertTripStamp(tripStamp: TripStamp) {
        addDisposable(
            tripRepository.insertTripStamp(tripStamp)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }
}