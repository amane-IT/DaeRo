package com.ssafy.daero.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {
    private int reportSeq;
    private int reporterSeq;
    private int reportedSeq;
    private String reportedAt;
    private int contentSeq;
    private int reportCategorySeq;
    private char handledYn;
    private String articleType;
}
