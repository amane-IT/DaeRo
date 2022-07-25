package com.ssafy.daero.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.nickname.SignupNicknameRequestDto
import com.ssafy.daero.data.repository.SignupRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SignupUsernameViewModel :BaseViewModel() {

    private val signupUsernameRepository = SignupRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress : LiveData<Boolean>
        get() = _showProgress

    val responseState = MutableLiveData<Int>()

    fun verifyNickname(nickname: SignupNicknameRequestDto){
        _showProgress.postValue(true)

        signupUsernameRepository.verifyNickname(nickname)
            .subscribe({ signupNicknameResponseDto ->
                if(signupNicknameResponseDto.availableYn == 'Y'){
                    responseState.postValue(SUCCESS)
                } else{
                    responseState.postValue(FAIL)
                }
                _showProgress.postValue(false)
            }, { throwable ->
                _showProgress.postValue(false)
                responseState.postValue(FAIL)
            })
    }
}