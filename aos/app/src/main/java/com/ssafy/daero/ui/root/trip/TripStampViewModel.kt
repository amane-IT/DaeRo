package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class TripStampViewModel() : BaseViewModel() {
    private val tripStampRepository = TripRepository.get()
    private val TAG = "TripNextVM_DaeRo"

    val showProgress = MutableLiveData<Boolean>()

    val responseState = MutableLiveData<Int>()

    fun insertTripStamp(tripStamp: TripStamp) {
        addDisposable(
            tripStampRepository.insertTripStamp(tripStamp)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("TripStampVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

}