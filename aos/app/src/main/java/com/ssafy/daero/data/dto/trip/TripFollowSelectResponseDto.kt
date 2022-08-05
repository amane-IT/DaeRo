package com.ssafy.daero.data.dto.trip

data class TripFollowSelectResponseDto (
    val image_url: String,
    val trip_place_seq: Int,
    val place_name: String
){
    var isSelected: Boolean = false
    constructor(image_url: String, trip_place_seq: Int, place_name: String, isSelected: Boolean): this(image_url, trip_place_seq, place_name){
        this.isSelected = isSelected
    }
}