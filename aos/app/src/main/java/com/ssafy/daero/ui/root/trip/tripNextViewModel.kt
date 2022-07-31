package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.repository.TripRepository

class tripNextViewModel : BaseViewModel() {
    private val tripNextRepositoy = TripRepository.get()

    val showProgress = MutableLiveData<Int>()

}