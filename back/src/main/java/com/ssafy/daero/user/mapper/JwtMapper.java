package com.ssafy.daero.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JwtMapper {
    @Select("select user_email from users where users_seq = #{userSeq} limit 1")
    String selectUserEmailByUserSeq(int userSeq);
}
