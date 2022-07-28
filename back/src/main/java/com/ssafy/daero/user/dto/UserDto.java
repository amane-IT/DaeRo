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
    private String emailVerifiedYn;
    private String passwordResetYn;
    private String suspendedYn;
    private String profileImageLink;
    private int reportedCount;
    private String createdAt;
    private String delYn;
    private String fcmToken;
}
