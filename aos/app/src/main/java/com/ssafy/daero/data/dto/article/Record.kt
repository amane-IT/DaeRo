package com.ssafy.daero.data.dto.article

data class Record(
    val datetime: String,
    val day_comment: String,
    val trip_stamps: List<TripStamp>
)
