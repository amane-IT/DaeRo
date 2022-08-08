package com.ssafy.daero.ui.root.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.badge.StampResponseDto
import com.ssafy.daero.data.repository.UserRepository

class StampViewModel: BaseViewModel() {
    private val TAG = "StampVM_DaeRo"
    private val stampRepository = UserRepository.get()

    private val _stampCount = MutableLiveData<StampResponseDto>()
    val stampCount: LiveData<StampResponseDto>
        get() = _stampCount

    var stampState = MutableLiveData<Int>()

    fun getBadges(){
        addDisposable(
            stampRepository.getBadges()
                .subscribe({ response ->
                    _stampCount.postValue(response.body())
                }, { throwable ->
                    Log.d(TAG, "getBadges: ${throwable.toString()}")
                })
        )
    }
}