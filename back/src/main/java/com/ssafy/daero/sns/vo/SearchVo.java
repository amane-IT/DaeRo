package com.ssafy.daero.sns.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchVo {
    String user;
    String article;
    String content;
    String place;
    int page = 1;
}
