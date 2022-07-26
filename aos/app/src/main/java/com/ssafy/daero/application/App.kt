package com.ssafy.daero.application

import android.app.Application
import com.ssafy.daero.BuildConfig
import com.ssafy.daero.data.repository.ServiceRepository
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.constant.UserSharedPreferences
import com.ssafy.daero.utils.preference.PreferenceUtil

class App : Application() {
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        //prefs = UserSharedPreferences(applicationContext)

        super.onCreate()
        initRepository()
    }

    private fun initRepository() {
        UserRepository.initialize(this)
        ServiceRepository.initialize(this)
        SnsRepository.initialize(this)
        TripRepository.initialize(this)
    }

    companion object {
        lateinit var userId : String
        lateinit var password : String
        var userName : String = ""
        var userSeq : Int = 0
        
        lateinit var prefs: PreferenceUtil
    }
}