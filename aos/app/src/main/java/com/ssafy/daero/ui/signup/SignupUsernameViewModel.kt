package com.ssafy.daero.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.signup.SignupNicknameRequestDto
import com.ssafy.daero.data.dto.signup.SignupRequestDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SignupUsernameViewModel : BaseViewModel() {

    private val userRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val responseState_nickname = MutableLiveData<Int>()
    val responseState_signup = MutableLiveData<Int>()

    fun verifyNickname(nickname: SignupNicknameRequestDto) {
        _showProgress.postValue(true)

        addDisposable(
            userRepository.verifyNickname(nickname)
                .subscribe({ response ->
                    if (response.body()!!.available_YN == 'y') {
                        responseState_nickname.postValue(SUCCESS)
                    } else {
                        responseState_nickname.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { throwable ->
                    Log.d("SignupUserNameVM_DaeRo", throwable.toString())
                    _showProgress.postValue(false)
                    responseState_nickname.postValue(FAIL)
                })
        )
    }

    fun signup(signupRequestDto: SignupRequestDto) {
        _showProgress.postValue(true)

        addDisposable(
            userRepository.signup(signupRequestDto)
                .subscribe({ response ->
                    if(response.body()!!.jwt.isNotEmpty()) {
                        _showProgress.postValue(false)
                        responseState_signup.postValue(SUCCESS)
                        App.prefs.jwt = response.body()!!.jwt
                    }
                }, { throwable ->
                    Log.d("SignupUsernameVM_DaeRo", throwable.toString())
                    _showProgress.postValue(false)
                    responseState_signup.postValue(FAIL)
                })
        )
    }
}