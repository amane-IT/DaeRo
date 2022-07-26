package com.ssafy.daero.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.login.FindPasswordModifyRequestDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class FindPasswordViewModel : BaseViewModel() {
    private val findIDRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val responseState = MutableLiveData<Int>()

    fun findPasswordModify(
        findPasswordModifyRequestDto: FindPasswordModifyRequestDto,
        reset_seq: String
    ) {
        _showProgress.postValue(true)

        addDisposable(
            findIDRepository.findPasswordModify(findPasswordModifyRequestDto, reset_seq)
                .subscribe({ findPasswordModifyResponseDto ->
                    if (findPasswordModifyResponseDto.result == 'Y') {
                        responseState.postValue(SUCCESS)
                    } else {
                        responseState.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { throwable ->
                    Log.d("FindPasswordVM_DaeRo", throwable.toString())
                    _showProgress.postValue(false)
                    responseState.postValue(FAIL)
                })
        )
    }
}