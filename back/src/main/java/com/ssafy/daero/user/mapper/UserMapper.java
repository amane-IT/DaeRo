package com.ssafy.daero.user.mapper;

import com.ssafy.daero.user.dto.EmailVerificationDto;
import com.ssafy.daero.user.dto.PasswordResetDto;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.vo.ChangePasswordVo;
import com.ssafy.daero.user.vo.SignupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int insertUser(String userEmail);

    int insertPasswordResetKey(PasswordResetDto passwordResetDto);

    int insertEmailVerificationKey(EmailVerificationDto emailVerificationDto);

    UserDto selectById(String id);

    UserDto selectByUserSeq(int user_seq);

    int selectFollowerByUserSeq(@Param("profile_user_seq") int profile_user_seq, @Param("current_user_seq") int current_user_seq);

    int selectFollowerCountById(int user_seq);

    int selectFollowingCountById(int user_seq);

    // badge 개수
    int selectAllBadgeById(int user_seq);

    UserDto selectEmailVerified(int userSeq);

    int selectNicknameCount(String nickname);

    UserDto selectUserByUserEmail(String userEmail);

    UserDto selectUserByUserSeq(int userSeq);

    int updateUser(SignupVo signupVo);

    int updateUserPassword(ChangePasswordVo changePasswordVo);

    // 유저 프로필 수정
    int updateProfile(@Param("user_seq") int user_seq, @Param("nickname") String nickname, @Param("profileUrl") String url);

    // 회원 탈퇴
    int deleteUser(int user_seq);
}
