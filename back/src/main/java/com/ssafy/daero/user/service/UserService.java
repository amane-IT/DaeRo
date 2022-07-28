package com.ssafy.daero.user.service;

import com.ssafy.daero.user.dto.EmailVerificationDto;
import com.ssafy.daero.user.dto.PasswordResetDto;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.mapper.UserMapper;
import com.ssafy.daero.user.vo.ChangePasswordVo;
import com.ssafy.daero.user.vo.SignupVo;
import com.ssafy.daero.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class UserService {
    private final String URL_PREFIX = "127.0.0.1";

    private final UserMapper userMapper;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserMapper userMapper, JavaMailSender mailSender) {
        this.userMapper = userMapper;
        this.mailSender = mailSender;
    }

    public boolean signup(SignupVo signupVo) {
        return userMapper.updateUser(signupVo) == 1;
    }

    public boolean checkEmailVerified(int userSeq) {
        return userMapper.selectEmailVerified(userSeq).getEmailVerifiedYn().equals("y");
    }

    public boolean checkPasswordByUserSeq(int userSeq, String password) {
        UserDto userDto = userMapper.selectUserByUserSeq(userSeq);
        if (userDto == null) {
            return false;
        }
        ChangePasswordVo changePasswordVo = new ChangePasswordVo();
        changePasswordVo.setPassword(password);
        return userDto.getPassword().equals(changePasswordVo.getHashedPassword());
    }

    public boolean changePasswordByUserSeq(int userSeq, String newPassword) {
        ChangePasswordVo changePasswordVo = new ChangePasswordVo();
        changePasswordVo.setUserSeq(userSeq);
        changePasswordVo.setPassword(newPassword);
        return userMapper.updateUserPassword(changePasswordVo) == 1;
    }

    public int findUserSeq(String userEmail) {
        UserDto userDto = userMapper.selectUserByUserEmail(userEmail);
        if (userDto == null) {
            return 0;
        }
        return userDto.getUserSeq();
    }

    public boolean nicknameAvailable(String nickname) {
        return userMapper.selectNicknameCount(nickname) > 0;
    }

    public boolean resetPassword(String userEmail) throws MessagingException {
        UserDto userDto = userMapper.selectUserByUserEmail(userEmail);
        if (userDto == null) {
            return false;
        }
        String resetKey = CryptoUtil.Sha512.hash(String.format("%f%s%f",
                Math.random(),
                userEmail,
                Math.random()));
        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setResetKey(resetKey);
        passwordResetDto.setUserSeq(userDto.getUserSeq());
        if (userMapper.insertPasswordResetKey(passwordResetDto) != 1) {
            return false;
        }

        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(userDto.getUserEmail());
        helper.setSubject("[DaeRo] 비밀번호 재설정 링크");
        helper.setText(String.format("<a href=\"%s/users/email/%s\" target=\"_blank\">인증하기</a>", URL_PREFIX, resetKey), true);
        this.mailSender.send(message);

        return true;
    }

    public boolean verifyEmail(String userEmail) throws MessagingException {
        if (userMapper.insertUser(userEmail) == 0) {
            return false;
        }
        UserDto userDto = userMapper.selectUserByUserEmail(userEmail);
        if (userDto == null) {
            return false;
        }
        String verificationKey = CryptoUtil.Sha512.hash(String.format("%f%s%f",
                Math.random(),
                userEmail,
                Math.random()));
        EmailVerificationDto emailVerificationDto = new EmailVerificationDto();
        emailVerificationDto.setVerificationKey(verificationKey);
        emailVerificationDto.setUserSeq(userDto.getUserSeq());
        if (userMapper.insertEmailVerificationKey(emailVerificationDto) != 1) {
            return false;
        }

        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(userDto.getUserEmail());
        helper.setSubject("[DaeRo] 회원가입 이메일 인증");
        helper.setText(String.format("<a href=\"%s/users/email/%s\" target=\"_blank\">인증하기</a>", URL_PREFIX, verificationKey), true);
        this.mailSender.send(message);

        return true;
    }
}
