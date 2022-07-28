package com.ssafy.daero.user.service;

import com.ssafy.daero.user.dto.EmailVerificationDto;
import com.ssafy.daero.user.dto.PasswordResetDto;
import com.ssafy.daero.user.mapper.MailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailMapper mailMapper;

    @Autowired
    public MailService(MailMapper mailMapper) {
        this.mailMapper = mailMapper;
    }

    public boolean verifyEmail(String verificationKey) {
        EmailVerificationDto emailVerificationDto = mailMapper.selectEmailVerificationByKey(verificationKey);
        if (emailVerificationDto == null) {
            return false;
        }
        if (this.mailMapper.updateUserEmailVerified(emailVerificationDto.getUserSeq()) != 1
                || this.mailMapper.updateUserEmailVerificationKeyExpired(emailVerificationDto.getEmailVerificationSeq()) != 1) {
            return false;
        }
        return true;
    }

    public boolean resetPassword(String resetKey) {
        PasswordResetDto passwordResetDto = mailMapper.selectPasswordResetByKey(resetKey);
        if (passwordResetDto == null) {
            return false;
        }
        if (this.mailMapper.updatePasswordResetCompleted(passwordResetDto.getPasswordResetSeq()) != 1
                || this.mailMapper.updatePasswordResetKeyExpired(passwordResetDto.getPasswordResetSeq()) != 1) {
            return false;
        }
        return true;
    }
}
