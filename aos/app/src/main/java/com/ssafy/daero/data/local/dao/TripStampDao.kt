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

    @Delete
    fun deleteStamp(tripStamp: TripStamp): Completable

    @Query("DELETE FROM $TRIP_STAMP")
    fun deleteAllStamps(): Completable

    @Query("UPDATE $TRIP_STAMP SET image_url = :imageUrl, satisfaction = :satisfaction WHERE id = :id")
    fun updateTripStamp(id: Int, imageUrl: String, satisfaction: Char): Completable

    @Query("SELECT * FROM $TRIP_STAMP WHERE id = :id")
    fun getTripStamp(id: Int): Single<TripStamp>
}