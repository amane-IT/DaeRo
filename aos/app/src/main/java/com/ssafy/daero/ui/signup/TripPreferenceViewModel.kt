package com.ssafy.daero.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.signup.TripPreferenceResponseDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class TripPreferenceViewModel: BaseViewModel() {

    private val tripPreferenceRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress : LiveData<Boolean>
        get() = _showProgress

    val responseState_getPreference = MutableLiveData<Int>()
    var preferenceList : MutableList<TripPreferenceResponseDto> = mutableListOf()

    private var userSeq = 0

    fun getPreferences(userSeq: Int){
        _showProgress.postValue(true)

        addDisposable(
            tripPreferenceRepository.getPreference(userSeq)
                .subscribe({ TripPreferenceResponseDto ->
                    if(TripPreferenceResponseDto.isNotEmpty()){
                        responseState_getPreference.postValue(SUCCESS)
                        preferenceList = TripPreferenceResponseDto

                    } else {
                        responseState_getPreference.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                }, { throwable ->
                    _showProgress.postValue(false)
                    responseState_getPreference.postValue(FAIL)
                })
        )
    }
}