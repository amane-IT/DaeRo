package com.ssafy.daero.user.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVo {
    private String id;
    private String password;

    public String getHashedPassword() {
        return password;
    }
}