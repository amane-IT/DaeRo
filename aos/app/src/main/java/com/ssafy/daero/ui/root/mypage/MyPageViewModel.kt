package com.ssafy.daero.ui.root.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.MyJourneyResponseDto
import com.ssafy.daero.data.dto.trip.TripAlbumItem
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

    private val _myAlbum = MutableLiveData<PagingData<TripAlbumItem>>()
    val myAlbum: LiveData<PagingData<TripAlbumItem>>
        get() = _myAlbum


    val getProfileState = MutableLiveData<Int>()
    val getJourneyState = MutableLiveData<Int>()

    fun getUserProfile() {
        addDisposable(
            userRepository.getUserProfile(App.prefs.userSeq).subscribe(
                { response ->
                    response.body()?.let {
                        App.prefs.nickname = it.nickname
                    }
                    _userProfile.postValue(response.body())
                }, { throwable ->
                    if (throwable is HttpException) {
                    }
                    getProfileState.postValue(FAIL)
                })
        )
    }

    fun getMyJourney(startDate: String, endDate: String) {
        addDisposable(
            tripRepository.getMyJourney(App.prefs.userSeq, startDate, endDate).subscribe(
                { response ->
                    when (response.code()) {
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
                    if (throwable is HttpException) {
                    }
                    getJourneyState.postValue(FAIL)
                }
            )
        )
    }

    fun getMyAlbum() {
        addDisposable(
            tripRepository.getMyAlbum()
                .cachedIn(viewModelScope)
                .subscribe(
                    {
                        _myAlbum.postValue(it)
                    }, { throwable ->
                    }
                )
        )
    }
}