package com.ssafy.daero.trip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripStampDto {
    private int tripStampSeq;
    private int tripDaySeq;
    private int tripPlaceSeq;
    private String imageUrl;
    private char placeSatisfactionYn;
}
