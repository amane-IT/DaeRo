package com.ssafy.daero.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.resetPassword.ResetPasswordRequestDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ResetPasswordConfirmViewModel : BaseViewModel() {

    private val userRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val responseState = MutableLiveData<Int>()

    fun confirmPassword(passwordRequestDto: ResetPasswordRequestDto){
        _showProgress.postValue(true)

        addDisposable(
            userRepository.confirmPassword(App.prefs.getUserSeq(-1), passwordRequestDto)
                .subscribe({ resetPasswordResponseDto ->
                    if(resetPasswordResponseDto.result == 'Y'){
                        responseState.postValue(SUCCESS)
                    } else{
                        responseState.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { throwable ->
                    Log.d("ResetPasswordVM_DaeRo", throwable.toString())
                    _showProgress.postValue(false)
                    responseState.postValue(FAIL)
                })
        )
    }
}