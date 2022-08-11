package com.ssafy.daero.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.login.FindPasswordRequestDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class FindPasswordConfirmViewModel : BaseViewModel() {
    private val findIDRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val checkEmailResponseState = MutableLiveData<Int>()

    val confirmResponseState = MutableLiveData<Int>()

    var password_reset_seq: Int = 0

    fun emailCheck(email: String) {
        _showProgress.postValue(true)

        addDisposable(
            findIDRepository.emailCheck(email)
                .subscribe({ response ->
                    if (response.body()!!.password_reset_seq != 0) {
                        checkEmailResponseState.postValue(SUCCESS)
                        password_reset_seq = response.body()!!.password_reset_seq
                    } else {
                        checkEmailResponseState.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { throwable ->
                    _showProgress.postValue(false)
                    checkEmailResponseState.postValue(FAIL)
                })
        )
    }

    fun findPassword(findPasswordRequestDto: FindPasswordRequestDto) {
        _showProgress.postValue(true)

        addDisposable(
            findIDRepository.findPassword(findPasswordRequestDto)
                .subscribe({ response ->
                    if (response.body()!!.result == 'y') {
                        confirmResponseState.postValue(SUCCESS)
                    } else {
                        confirmResponseState.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { throwable ->
                    Log.d("FindPasswordConfirmVM_DaeRo", throwable.toString())
                    _showProgress.postValue(false)
                    confirmResponseState.postValue(FAIL)
                })
        )
    }
}