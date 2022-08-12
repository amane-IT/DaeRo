package com.ssafy.daero.ui.root.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.trip.MyJourneyResponseDto
import com.ssafy.daero.data.dto.trip.TripAlbumItem
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import retrofit2.HttpException

class OtherPageViewModel : BaseViewModel() {
    private val userRepository = UserRepository.get()
    private val tripRepository = TripRepository.get()
    private val snsRepository = SnsRepository.get()

    private val _userProfile = MutableLiveData<UserProfileResponseDto>()
    val userProfile: LiveData<UserProfileResponseDto>
        get() = _userProfile

    private val _myJourney = MutableLiveData<List<List<MyJourneyResponseDto>>>()
    val myJourney: LiveData<List<List<MyJourneyResponseDto>>>
        get() = _myJourney

    private val _album = MutableLiveData<PagingData<TripAlbumItem>>()
    val album: LiveData<PagingData<TripAlbumItem>>
        get() = _album


    val getProfileState = MutableLiveData<Int>()
    val followState = MutableLiveData<Int>()
    val unFollowState = MutableLiveData<Int>()
    val getJourneyState = MutableLiveData<Int>()

    fun getUserProfile(userSeq: Int) {
        addDisposable(
            userRepository.getUserProfile(userSeq).subscribe(
                { response ->
                    _userProfile.postValue(response.body())
                }, { throwable ->
                    if (throwable is HttpException) {
                    }
                    getProfileState.postValue(FAIL)
                })
        )
    }

    fun follow(userSeq: Int) {
        addDisposable(
            snsRepository.follow(userSeq)
                .subscribe({
                    followState.postValue(SUCCESS)
                }, { throwable ->
                    followState.postValue(FAIL)
                })
        )
    }

    fun unFollow(userSeq: Int) {
        addDisposable(
            snsRepository.unFollow(userSeq)
                .subscribe({
                    unFollowState.postValue(SUCCESS)
                }, { throwable ->
                    unFollowState.postValue(FAIL)
                })
        )
    }

    fun getJourney(userSeq: Int, startDate: String, endDate: String) {
        addDisposable(
            tripRepository.getJourney(userSeq, startDate, endDate).subscribe(
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

    fun getAlbum(userSeq: Int) {
        addDisposable(
            tripRepository.getAlbum(userSeq)
                .cachedIn(viewModelScope)
                .subscribe(
                    {
                        _album.postValue(it)
                    }, { throwable ->
                    }
                )
        )
    }
}