package com.ssafy.daero.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmailVerificationDto {
    private int emailVerificationSeq;
    private int userSeq;
    private String verificationKey;
    private Date expiration;
    private char expiredYn;
}
