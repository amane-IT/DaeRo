package com.ssafy.daero.ui.root.trip

import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.repository.TripRepository

class TripFollowBottomSheetViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    fun deleteAllTripRecord() {
        addDisposable(
            tripRepository.deleteAllTripFollow().subscribe()
        )
        addDisposable(
            tripRepository.deleteAllTripStamps().subscribe()
        )
    }
}


