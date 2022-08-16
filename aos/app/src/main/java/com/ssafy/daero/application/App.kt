package com.ssafy.daero.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.daero.data.dto.user.FCMTokenRequestDto
import com.ssafy.daero.data.repository.ServiceRepository
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.preference.PreferenceUtil

class App : Application() {
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
        initRepository()
        initFCMMessageAccept()

        KakaoSdk.init(this, "1e255b1b25b5f7018b193713906bc430")
    }

    private fun initRepository() {
        UserRepository.initialize(this)
        ServiceRepository.initialize(this)
        SnsRepository.initialize(this)
        TripRepository.initialize(this)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initFCMMessageAccept() {
        // FCM 토큰 받아오기
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) {
                return@addOnCompleteListener
            }
            // 새로운 FCM 등록 토큰을 얻음
            prefs.ftoken = it.result
            createNotificationChannel("daero", "daero")

            if (prefs.userSeq != 0 && prefs.jwt != null) {
                UserRepository.get().updateFcmToken(prefs.userSeq, FCMTokenRequestDto(it.result))
                    .subscribe({}, { })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String) {
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(
                NotificationChannel(
                    id,
                    name,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
    }

    companion object {
        lateinit var userId: String
        lateinit var password: String
        var isDone = false
        var isEdit = false
        var keyword: String? = null

        lateinit var prefs: PreferenceUtil
    }
}