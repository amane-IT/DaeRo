package com.ssafy.daero.data.dto.trip

data class TripAlbumResponseDto(
    val trip_seq : Int,
    val image_url : String,
    val title : String,
    val expose : Char,
    val like_yn : Char
)

