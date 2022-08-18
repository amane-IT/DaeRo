package com.ssafy.daero.ui.root.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.badge.StampResponseDto
import com.ssafy.daero.data.repository.UserRepository

class StampViewModel : BaseViewModel() {
    private val stampRepository = UserRepository.get()

    private val _stampCount = MutableLiveData<StampResponseDto>()
    val stampCount: LiveData<StampResponseDto>
        get() = _stampCount

    var stampState = MutableLiveData<Int>()

    fun getBadges(userSeq: Int) {
        addDisposable(
            stampRepository.getBadges(userSeq)
                .subscribe({ response ->
                    _stampCount.postValue(response.body())
                }, { throwable ->
                })
        )
    }
}