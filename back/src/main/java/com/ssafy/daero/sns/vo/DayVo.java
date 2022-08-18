package com.ssafy.daero.sns.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class DayVo {
    private String datetime;
    private String dayComment;
    private ArrayList<StampVo> tripStamps;
}
