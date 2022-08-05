package com.ssafy.daero.user.vo;

import com.ssafy.daero.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo extends UserDto {
    public enum UserVoResult {
        SUCCESS, FAILURE, SUSPENDED_USER
    }
    private UserVoResult result;
}
