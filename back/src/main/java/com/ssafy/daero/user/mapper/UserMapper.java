package com.ssafy.daero.user.mapper;

import com.ssafy.daero.user.vo.SignupVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insertUser(SignupVo signupVo);
}
