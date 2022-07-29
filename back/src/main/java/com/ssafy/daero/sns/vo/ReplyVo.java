package com.ssafy.daero.sns.vo;

import com.ssafy.daero.sns.dto.ReplyDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReplyVo extends ReplyDto {

    public enum ReplyResult {
        NO_SUCH_ARTICLE, NO_SUCH_REPLY, SUCCESS, UNAUTHORIZED, FAILURE
    }

    private ReplyResult result;
    private String nickname;
    private String profileUrl;
}
