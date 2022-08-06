package com.ssafy.daero.data.local.dao

import androidx.room.*
import com.ssafy.daero.data.entity.TripFollow
import com.ssafy.daero.utils.constant.TRIP_FOLLOW
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface TripFollowDao {
    @Query("SELECT * FROM $TRIP_FOLLOW")
    fun getTripFollows(): Single<List<TripFollow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTripFollow(tripFollow: TripFollow): Completable

    @Update
    fun updateTripFollow(tripFollow: TripFollow): Completable

    @Delete
    fun deleteFollow(tripFollow: TripFollow): Completable

    @Query("DELETE FROM $TRIP_FOLLOW")
    fun deleteAllTripFollow(): Completable
}