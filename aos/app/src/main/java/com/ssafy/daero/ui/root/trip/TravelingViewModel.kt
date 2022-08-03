package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class TravelingViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val responseState = MutableLiveData<Int>()
    var articleTripStampData = listOf<TripStamp>()


    fun getTripStamps() {
        addDisposable(
            tripRepository.getTripStamps()
                .subscribe({
                    articleTripStampData = it
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
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