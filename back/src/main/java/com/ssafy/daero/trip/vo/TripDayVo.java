package com.ssafy.daero.trip.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class TripDayVo {
    private int tripSeq;
    private String datetime;
    private String dayComment;
    private LinkedList<TripStampVo> tripStamps;
    private int tripDaySeq;
}
