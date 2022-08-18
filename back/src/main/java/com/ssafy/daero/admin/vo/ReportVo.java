package com.ssafy.daero.admin.vo;

import com.ssafy.daero.admin.dto.ReportDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportVo extends ReportDto {
    private String reporterNickname;
    private String reportedNickname;
}
