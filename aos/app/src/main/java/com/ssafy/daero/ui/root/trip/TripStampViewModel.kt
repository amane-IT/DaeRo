package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class TripStampViewModel() : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val showProgress = MutableLiveData<Boolean>()

    val responseState = MutableLiveData<Int>()

    private val _tripStamp = MutableLiveData<TripStamp>()
    val tripStamp: LiveData<TripStamp>
        get() = _tripStamp

    val tripStampState = MutableLiveData<Int>()

    fun insertTripStamp(tripStamp: TripStamp) {
        addDisposable(
            tripRepository.insertTripStamp(tripStamp)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    responseState.postValue(FAIL)
                })
        )
    }

    fun updateTripStamp(id: Int, imageUrl: String, satisfaction: Char) {
        addDisposable(
            tripRepository.updateTripStamp(id, imageUrl, satisfaction)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    responseState.postValue(FAIL)
                })
        )
    }

    fun getTripStamp(id: Int) {
        addDisposable(
            tripRepository.getTripStamp(id)
                .subscribe({
                    _tripStamp.postValue(it)
                }, { throwable ->
                    tripStampState.postValue(FAIL)
                })
        )
    }

}