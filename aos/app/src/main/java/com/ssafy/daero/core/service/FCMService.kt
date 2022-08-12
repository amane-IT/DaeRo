package com.ssafy.daero.core.service

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.daero.application.App
import com.ssafy.daero.application.MainActivity
import com.ssafy.daero.data.dto.user.FCMTokenRequestDto
import com.ssafy.daero.data.entity.Notification
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.data.repository.UserRepository
import com.ssafy.daero.utils.notification.getNotificationBuilder

class FCMService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        App.prefs.ftoken = token

        if (App.prefs.userSeq != 0 && App.prefs.jwt != null) {
            UserRepository.get().updateFcmToken(App.prefs.userSeq, FCMTokenRequestDto(token))
                .subscribe({}, { })
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // 알림 설정 안되있으면 리턴
        if (!App.prefs.isNotificationAllow) return

        message.notification?.let {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            TripRepository.get().insertNotification(
                Notification(title = it.title ?: "", content = it.body ?: "")
            ).subscribe({}, { })


            val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            NotificationManagerCompat.from(this).notify(
                101,
                getNotificationBuilder(
                    "daero",
                    it.title ?: "",
                    it.body ?: "",
                    pIntent
                ).build()
            )
        }
    }
}