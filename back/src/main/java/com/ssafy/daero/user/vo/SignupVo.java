package com.ssafy.daero.user.vo;

import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.common.util.CryptoUtil;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class SignupVo extends UserDto {
    public enum SignupResult {
        SUCCESS, FAILURE, NO_SUCH_USER,
    }

    private String user_email;
    private String hashedPassword;
    private SignupResult result;

    public void setUser_email(String user_email) {
        this.user_email = user_email;
        super.setUserEmail(user_email);
    }

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
