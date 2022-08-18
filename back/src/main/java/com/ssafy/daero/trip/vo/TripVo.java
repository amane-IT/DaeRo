package com.ssafy.daero.trip.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class TripVo {
    private String title;
    private String tripComment;
    private String tripExpenses;
    private int rating;
    private String expose;
    private LinkedList<TripDayVo> records;
    private int userSeq;
    private String createdAt;
    private String thumbnailUrl;
    private int thumbnailIndex;
    private int tripSeq;
}
