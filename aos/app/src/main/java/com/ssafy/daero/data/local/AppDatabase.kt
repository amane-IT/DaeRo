package com.ssafy.daero.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.daero.data.entity.TripFollow
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.local.dao.TripFollowDao
import com.ssafy.daero.data.local.dao.TripStampDao

@Database(
    entities = [TripFollow::class, TripStamp::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripFollowDao(): TripFollowDao
    abstract fun tripStampDao(): TripStampDao
}