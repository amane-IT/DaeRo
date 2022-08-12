package com.ssafy.daero.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class FindIdViewModel : BaseViewModel() {
    private val findIDRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val responseState = MutableLiveData<Int>()

    fun findID(email: String) {
        _showProgress.postValue(true)

        addDisposable(
            findIDRepository.findID(email)
                .subscribe({ response ->
                    if (response.body()!!.result) {
                        responseState.postValue(SUCCESS)
                    } else {
                        responseState.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { throwable ->
                    _showProgress.postValue(false)
                    responseState.postValue(FAIL)
                })
        )
    }
}