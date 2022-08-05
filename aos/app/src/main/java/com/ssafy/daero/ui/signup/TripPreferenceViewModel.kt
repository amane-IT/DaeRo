package com.ssafy.daero.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.signup.TripPreferenceResponseDto
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import retrofit2.HttpException

class TripPreferenceViewModel : BaseViewModel() {

    private val tripPreferenceRepository = UserRepository.get()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    val responseState_getPreference = MutableLiveData<Int>()
    val responseState_postPreference = MutableLiveData<Int>()

    var preferenceList: List<TripPreferenceResponseDto> = emptyList()

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int>
        get() = _count

    init {
        _count.value = 0
    }

    fun getPreferences() {
        _showProgress.postValue(true)

        addDisposable(
            tripPreferenceRepository.getPreference()
                .subscribe({ response ->
                    if (response.body()!!.isNotEmpty()) {
                        responseState_getPreference.postValue(SUCCESS)
                        preferenceList = response.body()!!

                    } else {
                        responseState_getPreference.postValue(FAIL)
                    }
                    _showProgress.postValue(false)
                    _count.postValue(0)
                    Log.d("TripPreferenceVM", "plusCount: $count")
                }, { throwable ->
                    _showProgress.postValue(false)
                    responseState_getPreference.postValue(FAIL)
                })
        )
    }

    fun postPreference(userSeq: Int, result: List<Int>){
        _showProgress.postValue(true)

        addDisposable(
            tripPreferenceRepository.postPreference(userSeq, result)
                .subscribe({
                    _showProgress.postValue(false)
                    responseState_postPreference.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("TripPreferenceVM_DaeRo", throwable.toString())
                    if(throwable is HttpException){
                        Log.d("TripPreferenceVM_DaeRo", throwable.code().toString())
                    }
                    _showProgress.postValue(false)
                    responseState_postPreference.postValue(FAIL)
                })
        )
    }

    fun plusCount(){
        _count.value = _count.value!!.plus(1)
        Log.d("TripPreferenceVM", "plusCount: ${_count.value}")
    }

    fun minusCount() {
        _count.value = _count.value?.minus(1)
        Log.d("TripPreferenceVM", "minusCount: ${_count.value}")
    }
}