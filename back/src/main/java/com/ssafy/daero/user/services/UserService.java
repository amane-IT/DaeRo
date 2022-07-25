package com.ssafy.daero.services;

import com.ssafy.daero.mapper.UserMapper;
import com.ssafy.daero.vo.SignupVo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean signup(SignupVo signupVo) {
        return userMapper.insertUser(signupVo) == 1;
    }
}
