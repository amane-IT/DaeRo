package com.ssafy.daero.user.vo;

import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.common.util.CryptoUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVo extends UserDto {
    private String id;
    private String hashedPassword;

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.hashedPassword = CryptoUtil.Sha512.hash(password);
    }
}