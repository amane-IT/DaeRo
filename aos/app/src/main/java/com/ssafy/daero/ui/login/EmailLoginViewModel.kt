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
import com.ssafy.daero.utils.view.toast
import retrofit2.HttpException

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
                .subscribe({ response ->
                    // jwt 토큰 저장
                    App.prefs.jwt = response.body()?.jwt ?: ""

                    // userSeq 저장
                    App.prefs.userSeq = response.body()?.user_seq ?: 0

                    // nickname 저장
                    App.prefs.nickname = response.body()?.user_nickname ?: ""

                    _showProgress.postValue(false)
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("EmailLoginVM_DaeRo", throwable.toString())
                    if(throwable is HttpException) {
                        // http error code
                        Log.d("EmailLoginVM_DaeRo", throwable.code().toString())
                    }

                    // jwt 토큰, user_seq 삭제
                    App.prefs.jwt = null
                    App.prefs.userSeq = 0

                    _showProgress.postValue(false)
                    responseState.postValue(FAIL)
                })
        )
    }
}