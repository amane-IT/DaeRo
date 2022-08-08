package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.TripStampDetailResponseDto
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.EMPTY
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class TripStampDetailViewModel: BaseViewModel() {
    private val TAG = "TripStampDetailVM_DaeRo"
    private val tripStampDetailRepository = TripRepository.get()

    val tripStamp_state = MutableLiveData<Int>()

    private val _tripStampDetail = MutableLiveData<TripStampDetailResponseDto>()
    val tripStampDetail: LiveData<TripStampDetailResponseDto>
        get() = _tripStampDetail

    fun getTripStampDetail(tripStampSeq: Int) {
        addDisposable(
            tripStampDetailRepository.getTripStampDetail(tripStampSeq)
                .subscribe ({ response ->
                    if (response.body()!!.place == "") {
                        tripStamp_state.postValue(EMPTY)
                    } else {
                        tripStamp_state.postValue(SUCCESS)
                        _tripStampDetail.postValue(response.body()!!)
                    }
                }, { throwable ->
                    Log.d(TAG, "getTripStampDetail: ${throwable.toString()}")
                    tripStamp_state.postValue(FAIL)
                })
        )
    }
}