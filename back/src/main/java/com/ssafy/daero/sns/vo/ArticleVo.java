package com.ssafy.daero.sns.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticleVo {
    private int userSeq;
    private String title;
    private String tripComment;
    private String tripExpenses;
    private int rating;
    private int likes;
    private int comments;
    private int tripSeq;
}
