package com.ssafy.daero.user.mapper;

import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.vo.SignupVo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    int insertUser(SignupVo signupVo);

    UserDto selectById(String id);

    UserDto selectByUserSeq(int user_seq);

    int selectFollowerByUserSeq(@Param("profile_user_seq") int profile_user_seq, @Param("current_user_seq") int current_user_seq);

    int selectFollowerCountById(int user_seq);

    int selectFollowingCountById(int user_seq);

    // badge 개수
    int selectAllBadgeById(int user_seq);

    // 유저 프로필 수정
    int updateProfile(@Param("user_seq") int user_seq, @Param("nickname") String nickname);

    // 회원 탈퇴
    int deleteUser(int user_seq);
}
