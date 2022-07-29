package com.ssafy.daero.ui.root.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import retrofit2.HttpException

class MyPageViewModel : BaseViewModel() {
    private val userRepository = UserRepository.get()

    private val _userProfile = MutableLiveData<UserProfileResponseDto>()
    val userProfile: LiveData<UserProfileResponseDto>
        get() = _userProfile

    val getProfileState = MutableLiveData<Int>()

    fun getUserProfile() {
        addDisposable(
            userRepository.getUserProfile(App.prefs.userSeq).subscribe(
                { response ->
                    _userProfile.postValue(response.body())
                }, { throwable ->
                    Log.d("ProfileSettingVM_DaeRo", throwable.toString())
                    if (throwable is HttpException) {
                        Log.d("ProfileSettingVM_DaeRo", throwable.code().toString())
                    }
                    getProfileState.postValue(FAIL)
                })
        )
    }

}