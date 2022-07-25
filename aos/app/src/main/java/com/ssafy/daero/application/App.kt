package com.ssafy.daero.application

import android.app.Application
import com.ssafy.daero.data.repository.SignupRepository
import com.ssafy.daero.data.repository.LoginRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initRepository()
    }

    private fun initRepository() {
        SignupRepository.initialize(this)
        LoginRepository.initialize(this)
    }
}
