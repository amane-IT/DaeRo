package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.repository.TripRepository

class TripStampViewModel() : BaseViewModel() {
    private val tripStampRepository = TripRepository.get()
    private val TAG = "TripNextVM_DaeRo"

    val showProgress = MutableLiveData<Boolean>()

}