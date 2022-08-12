package com.ssafy.daero.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.daero.ui.root.sns.TripStampDto
import com.ssafy.daero.utils.constant.TRIP_STAMP
import com.ssafy.daero.utils.time.toDate

@Entity(tableName = TRIP_STAMP)
data class TripStamp(
    @ColumnInfo(name = "trip_place_seq")
    var tripPlaceSeq: Int,

    @ColumnInfo(name = "place_name")
    var placeName: String,

    @ColumnInfo(name = "date_time")
    var dateTime: Long,

    @ColumnInfo(name = "image_url")
    var imageUrl: String,

    @ColumnInfo(name = "satisfaction")
    var satisfaction: Char
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toTripStampDto(): TripStampDto =
        TripStampDto(
            tripPlaceSeq = tripPlaceSeq,
            placeName = placeName,
            dateTime = dateTime,
            imageUrl = imageUrl,
            satisfaction = satisfaction,
            id = id,
            isThumbnail = false,
            day = dateTime.toDate()
        )
}
