package com.ssafy.daero.user.vo;

import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.util.CryptoUtil;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class SignupVo extends UserDto {
    private String hashedPassword;

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.hashedPassword = CryptoUtil.Sha512.hash(password);
    }

    @Override
    public void setCreatedAt(String createdAt) {
        Date createdDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
        super.setCreatedAt(format.format(createdDate));
    }
}
