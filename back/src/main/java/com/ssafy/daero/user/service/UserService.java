package com.ssafy.daero.user.service;

import com.ssafy.daero.user.dto.EmailVerificationDto;
import com.ssafy.daero.user.dto.PasswordResetDto;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.mapper.UserMapper;
import com.ssafy.daero.user.vo.ChangePasswordVo;
import com.ssafy.daero.user.vo.LoginVo;
import com.ssafy.daero.user.vo.SignupVo;
import com.ssafy.daero.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    private final String URL_PREFIX = "127.0.0.1";
    private final String NO_REPLY = "no-reply@daero.com";

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

    public UserDto login(LoginVo loginVO) {
        UserDto userDto = userMapper.selectById(loginVO.getId());
        // 존재하는 유저고, 비밀번호도 맞고, 이용정지당한 유저가 아니고, 이메일 인증을 완료한 유저
        if (userDto != null && Objects.equals(userDto.getPassword(), loginVO.getHashedPassword())
                && userDto.getSuspendedYn() == 'n' && userDto.getEmailVerifiedYn() == 'y') {
            return userDto;
        } else {
            return null;
        }
    }

    public UserDto loginJwt(int userSeq) {
        return userMapper.selectByUserSeq(userSeq);
    }

    public boolean findId(String email) {
        UserDto currentUser = userMapper.selectById(email);
        return currentUser != null;
    }

    public Map<String, Object> userProfile(int userSeq, int currentUserSeq) {
        Map<String, Object> profile = new HashMap<>();
        UserDto profileUser = userMapper.selectByUserSeq(userSeq);
        // userJwt Decoder사용해 현재 로그인 중인 유저 seq 얻기 -> follow 여부 확인 (본인이면 무조건 n)
        // 1. 가입한 적 없는 유저
        // 2. 탈퇴한 유저
        if (profileUser.getUserEmail() == null) {
            return null;
        }
        profile.put("nickname", profileUser.getNickname());
        profile.put("profile_url", profileUser.getProfileImageLink());
        profile.put("follower", userMapper.selectFollowerCountById(userSeq));
        profile.put("following", userMapper.selectFollowingCountById(userSeq));
        profile.put("badge_count", userMapper.selectAllBadgeById(userSeq));
        if (userSeq == currentUserSeq) {
            profile.put("followYn", "n");
        } else if (userMapper.selectFollowerByUserSeq(profileUser.getUserSeq(), currentUserSeq) == 1) {
            profile.put("followYn", "y");
        } else {
            profile.put("followYn", "n");
        }
        return profile;
    }

    public boolean updateUserProfile(int userSeq, String nickname) {
        int res = userMapper.updateProfile(userSeq, nickname);
        return res == 1;
    }

    public boolean leaveUser(int userSeq) {
        int res = userMapper.deleteUser(userSeq);
        return res == 1;
    }

    public boolean checkEmailVerified(int userSeq) {
        return userMapper.selectEmailVerified(userSeq).getEmailVerifiedYn() == 'y';
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
        return userMapper.selectNicknameCount(nickname) == 0;
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
        helper.setFrom(NO_REPLY);
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
        helper.setFrom(NO_REPLY);
        helper.setTo(userDto.getUserEmail());
        helper.setSubject("[DaeRo] 회원가입 이메일 인증");
        helper.setText(String.format("<a href=\"%s/users/email/%s\" target=\"_blank\">인증하기</a>", URL_PREFIX, verificationKey), true);
        this.mailSender.send(message);

        return true;
    }
}
