package com.ssafy.daero.trip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripPlaceDto {
    private int tripPlaceSeq;
    private String placeName;
    private String address;
    private Double latitude;
    private Double longitude;
    private String description;
    private int regionSeq;
    private String imageUrl;
}
