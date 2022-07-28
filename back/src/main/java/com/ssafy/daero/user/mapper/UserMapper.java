package com.ssafy.daero.user.mapper;

import com.ssafy.daero.user.dto.EmailVerificationDto;
import com.ssafy.daero.user.dto.PasswordResetDto;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.vo.ChangePasswordVo;
import com.ssafy.daero.user.vo.SignupVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insertUser(String userEmail);

    int insertPasswordResetKey(PasswordResetDto passwordResetDto);

    int insertEmailVerificationKey(EmailVerificationDto emailVerificationDto);

    UserDto selectEmailVerified(int userSeq);

    int selectNicknameCount(String nickname);

    UserDto selectUserByUserEmail(String userEmail);

    UserDto selectUserByUserSeq(int userSeq);

    int updateUser(SignupVo signupVo);

    int updateUserPassword(ChangePasswordVo changePasswordVo);
}
