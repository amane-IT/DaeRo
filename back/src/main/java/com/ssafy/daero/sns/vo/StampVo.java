package com.ssafy.daero.sns.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StampVo {
    private String imageUrl;
    private int tripDaySeq;
    private int tripStampSeq;
    private double latitude;
    private double longitude;
    private String datetime;
    private String dayComment;
}
