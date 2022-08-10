package com.ssafy.daero.ui.setting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SettingViewModel : BaseViewModel() {
    private val userRepository = UserRepository.get()
    private val tripRepository = TripRepository.get()

    val withdrawalState = MutableLiveData<Int>()

    fun withdrawal() {
        addDisposable(
            userRepository.withdrawal(App.prefs.userSeq)
                .subscribe({
//                        response ->
//                    Log.d("탈퇴여부 체크", "withdrawal: ${response.body()}")
//                    if (response.body()!!) {
                        withdrawalState.postValue(SUCCESS)
//                    } else {
//                        withdrawalState.postValue(FAIL)
//                    }
                }, { throwable ->
                    Log.d("SettingVM_DaeRo", throwable.toString())
                    withdrawalState.postValue(FAIL)
                })
        )
    }

    fun deleteAllTripRecord() {
        addDisposable(
            tripRepository.deleteAllTripFollow().subscribe()
        )
        addDisposable(
            tripRepository.deleteAllTripStamps().subscribe()
        )
    }
}