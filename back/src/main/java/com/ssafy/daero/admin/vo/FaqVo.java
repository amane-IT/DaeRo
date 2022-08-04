package com.ssafy.daero.admin.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqVo {
    private int faqSeq;
    private String title;
    private String content;
    private int adminSeq;
}
