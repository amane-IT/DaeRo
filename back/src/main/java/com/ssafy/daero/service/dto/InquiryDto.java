package com.ssafy.daero.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryDto {
    private String title;
    private String content;
    private String created_at;
    private String answer;
    private String answer_at;
    private char answer_yn;
}
