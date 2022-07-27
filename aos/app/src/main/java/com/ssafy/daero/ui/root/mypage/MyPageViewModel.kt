package com.ssafy.daero.ui.root.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.MyJourneyResponseDto
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import retrofit2.HttpException

class MyPageViewModel : BaseViewModel() {
    private val userRepository = UserRepository.get()
    private val tripRepository = TripRepository.get()

    private val _userProfile = MutableLiveData<UserProfileResponseDto>()
    val userProfile: LiveData<UserProfileResponseDto>
        get() = _userProfile

    private val _myJourney = MutableLiveData<List<List<MyJourneyResponseDto>>>()
    val myJourney: LiveData<List<List<MyJourneyResponseDto>>>
        get() = _myJourney

    val getProfileState = MutableLiveData<Int>()
    val getJourneyState = MutableLiveData<Int>()

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

    fun getMyJourney(startDate: String, endDate: String) {
        addDisposable(
            tripRepository.getMyJourney(App.prefs.userSeq, startDate, endDate).subscribe(
                { response ->
                    when(response.code()) {
                        204 -> {
                            // 아무 여행도 안했을 때
                            getJourneyState.postValue(SUCCESS)
                        }
                        else -> {
                            _myJourney.postValue(response.body())
                        }
                    }
                },
                { throwable ->
                    Log.d("ProfileSettingVM_DaeRo", throwable.toString())
                    if (throwable is HttpException) {
                        Log.d("ProfileSettingVM_DaeRo", throwable.code().toString())
                    }
                    getJourneyState.postValue(FAIL)
                }
            )
        )
    }

}