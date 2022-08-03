package com.ssafy.daero.data.local.dao

import androidx.room.*
import com.ssafy.daero.data.entity.Notification
import com.ssafy.daero.data.entity.TripFollow
import com.ssafy.daero.utils.constant.NOTIFICATION
import com.ssafy.daero.utils.constant.TRIP_FOLLOW
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface NotificationDao {
    @Query("SELECT * FROM $NOTIFICATION")
    fun getNotifications(): Single<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: Notification): Completable

    @Update
    fun updateNotification(notification: Notification): Completable

    @Delete
    fun deleteNotification(notification: Notification): Completable

    @Query("DELETE FROM $NOTIFICATION")
    fun deleteAllNotifications(): Completable
}