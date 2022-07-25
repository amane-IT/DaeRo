package com.ssafy.daero.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.signup.email.SignupEmailRequestDto
import com.ssafy.daero.data.dto.signup.email.SignupEmailResponseDto
import com.ssafy.daero.data.repository.SignupRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import io.reactivex.rxjava3.core.Single

class SignupEmailViewModel: BaseViewModel() {

    private val signupEmailRepository = SignupRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress : LiveData<Boolean>
        get() = _showProgress

    val responseState_verifyEmail = MutableLiveData<Int>()
    val responseState_verifyUserEmail = MutableLiveData<Int>()

    private var userSeq = 0

    fun verifyEmail(signupEmailRequestDto: SignupEmailRequestDto){
        _showProgress.postValue(true)

        signupEmailRepository.verifyEmail(signupEmailRequestDto)
            .subscribe({ signupEmailResponseDto ->
                if(signupEmailResponseDto.userSeq > 0){
                    responseState_verifyEmail.postValue(SUCCESS)
                    userSeq = signupEmailResponseDto.userSeq

                } else {
                    responseState_verifyEmail.postValue(FAIL)
                }
                _showProgress.postValue(false)
            }, { throwable ->
                _showProgress.postValue(false)
                responseState_verifyEmail.postValue(FAIL)
            })
    }

    fun verifyUserEmail(){
        _showProgress.postValue(true)

        signupEmailRepository.verifyUserEmail(SignupEmailResponseDto(userSeq))
            .subscribe({ verifyUserEmailResponseDto ->
                if(verifyUserEmailResponseDto.result == 'Y'){
                    responseState_verifyUserEmail.postValue(SUCCESS)
                    userSeq = 0
                } else {
                    responseState_verifyUserEmail.postValue(FAIL)
                }
                _showProgress.postValue(false)

            }, { throwable ->
                _showProgress.postValue(false)
                responseState_verifyUserEmail.postValue(FAIL)
            })
    }
}