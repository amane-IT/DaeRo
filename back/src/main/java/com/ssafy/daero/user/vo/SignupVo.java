package com.ssafy.daero.user.vo;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class SignupVo {
    private String userEmail;
    private String password;
    private String nickname;

    public String getHashedPassword() {

        return "";
    }

    public String getCreatedAt() {
        Date createdDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
        return format.format(createdDate);
    }
}
