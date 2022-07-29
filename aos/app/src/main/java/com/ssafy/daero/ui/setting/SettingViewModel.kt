package com.ssafy.daero.ui.setting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SettingViewModel : BaseViewModel() {
    private val userRepository = UserRepository.get()

    val withdrawalState = MutableLiveData<Int>()

    fun withdrawal() {
        addDisposable(
            userRepository.withdrawal(App.userSeq)
                .subscribe({ response ->
                    if (response.body()!!) {
                        withdrawalState.postValue(SUCCESS)
                    } else {
                        withdrawalState.postValue(FAIL)
                    }
                }, { throwable ->
                    Log.d("SettingVM_DaeRo", throwable.toString())
                    withdrawalState.postValue(FAIL)
                })
        )
    }
}