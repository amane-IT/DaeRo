package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.TripFollowSelectResponseDto
import com.ssafy.daero.data.entity.TripFollow
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class TripFollowViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()
    val tripFollowState = MutableLiveData<Int>()
    var TripFollowData = listOf<TripFollow>()
    var resultTripFollow = listOf<TripFollowSelectResponseDto>()


    fun getTripFollows() {
        addDisposable(
            tripRepository.getTripFollows()
                .subscribe({
                    TripFollowData = it
                    tripFollowState.postValue(SUCCESS)
                }, { throwable ->
                    tripFollowState.postValue(FAIL)
                })
        )
    }

    fun insertTripFollow(tripFollow: TripFollow) {
        addDisposable(
            tripRepository.insertTripFollow(tripFollow)
                .subscribe({
                    tripFollowState.postValue(SUCCESS)
                }, { throwable ->
                    tripFollowState.postValue(FAIL)
                })
        )
    }

    fun getTripFollow(articleSeq: Int) {
        addDisposable(
            snsRepository.getTripFollow(articleSeq)
                .subscribe({ response ->
                    resultTripFollow = response.body()!!
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    responseState.postValue(FAIL)
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

}