package com.ssafy.daero.user.vo;

import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.common.util.CryptoUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupVo extends UserDto {
    public enum SignupResult {
        SUCCESS, FAILURE, NO_SUCH_USER,
    }

    private String user_email;
    private String hashedPassword;
    private SignupResult result;
    private String createdAt;

    public void setUser_email(String user_email) {
        this.user_email = user_email;
        super.setUserEmail(user_email);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.hashedPassword = CryptoUtil.Sha512.hash(password);
    }
}
