package com.ssafy.daero.admin.vo;

import com.ssafy.daero.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo extends UserDto {
    private String regDate;
}
