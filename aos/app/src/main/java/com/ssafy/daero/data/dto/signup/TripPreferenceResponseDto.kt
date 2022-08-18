package com.ssafy.daero.data.dto.signup

data class TripPreferenceResponseDto(
    val place_seq : Int,
    val place_name : String,
    val image_url : String
){
    var isSelected: Boolean = false
    constructor(place_seq: Int, place_name: String, image_url: String, isSelected: Boolean): this(place_seq, place_name, image_url){
        this.isSelected = isSelected
    }
}
