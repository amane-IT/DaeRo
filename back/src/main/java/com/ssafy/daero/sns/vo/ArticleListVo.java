package com.ssafy.daero.sns.vo;

import com.ssafy.daero.sns.dto.ArticleDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleListVo extends ArticleDto {
    private String nickname;
    private int userSeq;
    private int tripSeq;
    private String profileUrl;
    private String description;
    private String startDate;
    private String endDate;
    private String likeYn;
}
