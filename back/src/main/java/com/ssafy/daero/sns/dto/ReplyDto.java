package com.ssafy.daero.sns.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDto {
    private int replySeq;
    private int articleSeq;
    private int userSeq;
    private String content;
    private String createdAt;
    private String updatedAt;
    private int rereplyCount;
    private int rereplyParent;
}
