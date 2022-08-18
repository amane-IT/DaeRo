package com.ssafy.daero.user.mapper;

import com.ssafy.daero.user.dto.EmailVerificationDto;
import com.ssafy.daero.user.dto.PasswordResetDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings("UnusedReturnValue")
@Mapper
public interface MailMapper {
    int insertUserAchievement(@Param("userSeq") int userSeq, @Param("regionCodes") int[] regionCodes);

    EmailVerificationDto selectEmailVerificationByKey(String emailVerificationKey);

    PasswordResetDto selectPasswordResetByKey(String passwordResetKey);

    int updateUserEmailVerified(int userSeq);

    int updateUserEmailVerificationKeyExpired(int emailVerificationSeq);

    int updatePasswordResetCompleted(int userSeq);

    int updatePasswordResetKeyExpired(int passwordResetKeySeq);
}