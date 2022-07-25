package com.ssafy.daero.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int userSeq;
    private String email;
    private String password;
    private String nickname;
    private char emailVerifiedYn;
    private String profileImageLink;
    private int reportedCount;
    private String createdAt;
    private char delYn;
    private String fcmToken;
    private char passwordFindYn;
}
