package com.ssafy.daero.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.signup.SignupEmailRequestDto
import com.ssafy.daero.data.dto.signup.SignupEmailResponseDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SignupEmailViewModel : BaseViewModel() {

    private val userRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val responseState_verifyEmail = MutableLiveData<Int>()
    val responseState_verifyUserEmail = MutableLiveData<Int>()

    fun verifyEmail(signupEmailRequestDto: SignupEmailRequestDto) {
        _showProgress.postValue(true)

        addDisposable(
            userRepository.verifyEmail(signupEmailRequestDto)
                .subscribe({ response ->
                    if (response.body()!!.user_seq > 0) {
                        responseState_verifyEmail.postValue(SUCCESS)
                        App.prefs.userSeq = response.body()!!.user_seq

                    } else {
                        responseState_verifyEmail.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { throwable ->
                    _showProgress.postValue(false)
                    responseState_verifyEmail.postValue(FAIL)
                })
        )
    }

    fun verifyUserEmail() {
        _showProgress.postValue(true)
        addDisposable(
            userRepository.verifyUserEmail(SignupEmailResponseDto(App.prefs.userSeq))
                .subscribe({ response ->
                    if (response.body()!!.result == 'y') {
                        responseState_verifyUserEmail.postValue(SUCCESS)
                    } else {
                        responseState_verifyUserEmail.postValue(FAIL)
                    }
                    _showProgress.postValue(false)

                }, { throwable ->
                    _showProgress.postValue(false)
                    responseState_verifyUserEmail.postValue(FAIL)
                })
        )
    }
}