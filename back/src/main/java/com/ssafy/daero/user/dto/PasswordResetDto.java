package com.ssafy.daero.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PasswordResetDto {
    private int passwordResetSeq;
    private int userSeq;
    private String resetKey;
    private Date expiration;
    private char expiredYn;
}
