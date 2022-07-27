package com.ssafy.daero.trip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripDto {
    private int tripSeq;
    private int userSeq;
    private String tripComment;
    private String tripExpenses;
    private int tripRating;
}
