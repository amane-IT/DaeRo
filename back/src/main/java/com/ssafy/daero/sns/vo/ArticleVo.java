package com.ssafy.daero.sns.vo;

import com.ssafy.daero.sns.dto.ArticleDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticleVo extends ArticleDto {
    private int userSeq;
    private String tripComment;
    private String tripExpenses;
    private int rating;
    private int likes;
    private int comments;
}
