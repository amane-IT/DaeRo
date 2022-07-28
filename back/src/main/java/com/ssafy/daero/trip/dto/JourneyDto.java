package com.ssafy.daero.trip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JourneyDto {
    private int tripSeq;
    private String createdAt;
    private int tripStampSeq;
    private Double latitude;
    private Double longitude;
}
