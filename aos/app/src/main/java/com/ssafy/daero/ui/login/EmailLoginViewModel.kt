package com.ssafy.daero.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.login.LoginRequestDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class EmailLoginViewModel : BaseViewModel() {
    private val userRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val responseState = MutableLiveData<Int>()

    fun emailLogin(loginRequestDto: LoginRequestDto) {
        _showProgress.postValue(true)

        addDisposable(
            userRepository.emailLogin(loginRequestDto)
                .subscribe({ loginResponseDto ->
                    // Todo : userSeq, jwt 를 sharedPreference 에 저장하기
                    // jwt 토큰 저장
                    Log.d("EmailLoginVM_DaeRo", loginResponseDto.toString())
                    App.prefs.jwt = loginResponseDto.jwt

                    _showProgress.postValue(false)
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("EmailLoginVM_DaeRo", throwable.toString())
                    _showProgress.postValue(false)
                    responseState.postValue(FAIL)
                })
        )
    }
}