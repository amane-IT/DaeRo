package com.ssafy.daero.data.local.dao

import androidx.room.*
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.utils.constant.TRIP_STAMP
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface TripStampDao {
    @Query("SELECT * FROM $TRIP_STAMP")
    fun getTripStamps(): Single<List<TripStamp>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTripStamp(tripStamp: TripStamp): Completable

    @Update
    fun updateTripStamp(tripStamp: TripStamp): Completable

    @Delete
    fun deleteStamp(tripStamp: TripStamp): Completable

    @Query("DELETE FROM $TRIP_STAMP")
    fun deleteAll(): Completable
}