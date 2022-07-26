package com.ssafy.daero.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.user.ProfileEditRequestDto
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import retrofit2.HttpException

class ProfileSettingViewModel : BaseViewModel() {
    private val userRepository = UserRepository.get()

    private val _userProfile = MutableLiveData<UserProfileResponseDto>()
    val userProfile: LiveData<UserProfileResponseDto>
        get() = _userProfile

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val editState = MutableLiveData<Int>()
    val getProfileState = MutableLiveData<Int>()

    fun getUserProfile() {
        _showProgress.postValue(true)

        addDisposable(
            userRepository.getUserProfile(App.prefs.userSeq).subscribe(
                { response ->
                    _userProfile.postValue(response.body())
                    _showProgress.postValue(false)
                }, { throwable ->
                    Log.d("ProfileSettingVM_DaeRo", throwable.toString())
                    if (throwable is HttpException) {
                        Log.d("ProfileSettingVM_DaeRo", throwable.code().toString())
                    }
                    _showProgress.postValue(false)
                    getProfileState.postValue(FAIL)
                })
        )
    }

    fun editProfile(profileEditRequestDto: ProfileEditRequestDto) {
        _showProgress.postValue(true)

        val userSeq = App.prefs.userSeq

        addDisposable(
            userRepository.editProfile(userSeq, profileEditRequestDto)
                .subscribe({
                    _showProgress.postValue(false)
                    editState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ProfileSettingVM_DaeRo", throwable.toString())
                    if (throwable is HttpException) {
                        Log.d("ProfileSettingVM_DaeRo", throwable.code().toString())
                    }
                    _showProgress.postValue(false)
                    editState.postValue(FAIL)
                })
        )
    }
}