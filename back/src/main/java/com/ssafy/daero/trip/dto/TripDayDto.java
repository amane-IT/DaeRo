package com.ssafy.daero.trip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripDayDto {
    private int tripDaySeq;
    private int tripSeq;
    private String date;
    private String dayComment;
}
