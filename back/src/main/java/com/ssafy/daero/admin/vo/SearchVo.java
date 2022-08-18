package com.ssafy.daero.admin.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchVo {
    private int page = 1;
    private String search;
}
