package com.ssafy.daero.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int userSeq;
    private String userEmail;
    private String password;
    private String nickname;
    private char emailVerifiedYn;
    private char passwordResetYn;
    private char suspendedYn;
    private String profileImageLink;
    private int reportedCount;
    private String createdAt;
    private char delYn;
    private String fcmToken;
}
