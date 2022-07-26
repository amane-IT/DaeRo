package com.ssafy.daero.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.login.ProfileEditRequest
import com.ssafy.daero.data.repository.LoginRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ProfileSettingViewModel : BaseViewModel() {
    private val loginRepository = LoginRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val responseState = MutableLiveData<Int>()

    fun editProfile(profileEditRequest: ProfileEditRequest) {
        _showProgress.postValue(true)

        // todo: userSeq 를 SharePreference 에서 가져오기
        val userSeq = 1

        addDisposable(
            loginRepository.editProfile(userSeq, profileEditRequest)
                .subscribe({
                    _showProgress.postValue(false)
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ProfileSettingVM_DaeRo", throwable.toString())
                    _showProgress.postValue(false)
                    responseState.postValue(FAIL)
                })
        )
    }
}