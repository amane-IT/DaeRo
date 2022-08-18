package com.ssafy.daero.sns.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
    private int articleSeq;
    private int tripSeq;
    private String title;
    private String createdAt;
    private String updatedAt;
    private int likeCount;
    private int replyCount;
    private char openYn;
    private String thumbnailUrl;
}
