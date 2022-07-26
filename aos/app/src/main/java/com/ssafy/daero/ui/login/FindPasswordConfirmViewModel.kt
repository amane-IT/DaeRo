package com.ssafy.daero.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.login.FindPasswordRequestDto
import com.ssafy.daero.data.repository.LoginRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class FindPasswordConfirmViewModel : BaseViewModel() {
    private val findIDRepository = LoginRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress : LiveData<Boolean>
        get() = _showProgress

    val responseState = MutableLiveData<Int>()

    fun findPassword(findPasswordRequestDto: FindPasswordRequestDto) {
        _showProgress.postValue(true)

        findIDRepository.findPassword(findPasswordRequestDto)
            .subscribe({ findPasswordResponseDto ->
                if(findPasswordResponseDto.result=='Y') {
                    responseState.postValue(SUCCESS)
                } else {
                    responseState.postValue(FAIL)
                }
                _showProgress.postValue(false)
            }, { throwable ->
                _showProgress.postValue(false)
                responseState.postValue(FAIL)
            })
    }
}