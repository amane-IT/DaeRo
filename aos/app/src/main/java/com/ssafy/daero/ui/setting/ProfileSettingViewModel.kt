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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

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

    private fun editJustProfile(profileEditRequestDto: ProfileEditRequestDto) {
        _showProgress.postValue(true)
        addDisposable(
            userRepository.editProfile(App.prefs.userSeq, profileEditRequestDto)
                .subscribe(
                    {
                        _showProgress.postValue(false)
                        editState.postValue(SUCCESS)
                    }, throwableBlock
                )
        )
    }

    fun editProfile(imagePath: String?, imageUrl: String, nickname: String) {
        _showProgress.postValue(true)

        if (imagePath == null) {
            editJustProfile(ProfileEditRequestDto(image_url = imageUrl, nickname = nickname))
        } else {
            addDisposable(
                userRepository.postImage(
                    MultipartBody.Part.createFormData(
                        "file",
                        imagePath,
                        File(imagePath).asRequestBody("image/jpeg".toMediaTypeOrNull())
                    )
                )
                    .subscribe(
                        {
                            editJustProfile(
                                ProfileEditRequestDto(
                                    image_url = it.body()!!.image_url,
                                    nickname = nickname
                                )
                            )
                        }, throwableBlock
                    )
            )
        }
    }

    private val throwableBlock: (Throwable) -> Unit = { throwable ->
        Log.d("ProfileSettingVM_DaeRo", throwable.toString())
        if (throwable is HttpException) {
            Log.d("ProfileSettingVM_DaeRo", throwable.code().toString())
        }
        _showProgress.postValue(false)
        editState.postValue(FAIL)
    }
}