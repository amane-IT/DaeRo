package com.ssafy.daero.trip.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class TripDayVo {
    private int tripSeq;
    private String datetime;
    private String dayComment;
    private ArrayList<TripStampVo> tripStamps;
}
