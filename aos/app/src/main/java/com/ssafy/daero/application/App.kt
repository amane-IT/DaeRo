package com.ssafy.daero.application

import android.app.Application
import com.ssafy.daero.data.repository.ServiceRepository
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.data.repository.UserRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initRepository()
    }

    private fun initRepository() {
        UserRepository.initialize(this)
        ServiceRepository.initialize(this)
        SnsRepository.initialize(this)
        TripRepository.initialize(this)
    }
}