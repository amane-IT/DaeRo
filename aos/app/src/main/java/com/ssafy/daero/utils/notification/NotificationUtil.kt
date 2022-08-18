package com.ssafy.daero.utils.notification

import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.NotificationCompat
import com.ssafy.daero.R

fun Context.getNotificationBuilder(
    channelId: String,
    title: String,
    content: String,
    pIntent: PendingIntent
): NotificationCompat.Builder =
    NotificationCompat.Builder(this, channelId)
        .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_daero_vector))
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentTitle(title)
        .setContentText(content)
        .setAutoCancel(true)
        .setContentIntent(pIntent)

