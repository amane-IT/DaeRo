package com.ssafy.daero.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.daero.utils.constant.TRIP_FOLLOW
import com.ssafy.daero.utils.constant.TRIP_STAMP

@Entity(tableName = TRIP_FOLLOW)
data class TripFollow(
    @ColumnInfo(name = "trip_place_seq")
    var tripPlaceSeq: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
