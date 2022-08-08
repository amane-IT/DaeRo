package com.ssafy.daero.user.service;

import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.trip.vo.RecommendTagVo;
import com.ssafy.daero.user.dto.EmailVerificationDto;
import com.ssafy.daero.user.dto.PasswordResetDto;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.mapper.UserMapper;
import com.ssafy.daero.user.vo.ChangePasswordVo;
import com.ssafy.daero.user.vo.LoginVo;
import com.ssafy.daero.user.vo.SignupVo;
import com.ssafy.daero.common.util.CryptoUtil;
import com.ssafy.daero.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class UserService {
    private final String URL_PREFIX = "http://i7d110.p.ssafy.io";
    private final String NO_REPLY = "no-reply@daero.com";

    private final UserMapper userMapper;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserMapper userMapper, JavaMailSender mailSender) {
        this.userMapper = userMapper;
        this.mailSender = mailSender;
    }

    public SignupVo signup(SignupVo signupVo) {
        if (userMapper.updateUser(signupVo) != 1) {
            signupVo.setResult(SignupVo.SignupResult.NO_SUCH_USER);
            return signupVo;
        }
        UserDto userDto = userMapper.selectUserByUserEmail(signupVo.getUserEmail());
        if (userDto == null) {
            signupVo.setResult(SignupVo.SignupResult.NO_SUCH_USER);
            return signupVo;
        }
        signupVo.setUserSeq(userDto.getUserSeq());
        signupVo.setResult(SignupVo.SignupResult.SUCCESS);
        return signupVo;
    }

    public UserVo login(LoginVo loginVO) {
        UserVo userVo = userMapper.selectById(loginVO.getId());
        // 아이디가 존재하지 않는 유저일 경우
        if (userVo == null) { return null; }
        Date now = new Date();
        // 탈퇴하지 않는 유저고, 비밀번호도 맞고, 이메일 인증을 완료한 유저
        if (userVo.getDelYn() == 'n' && Objects.equals(userVo.getPassword(), loginVO.getHashedPassword())
                && userVo.getEmailVerifiedYn() == 'y') {
            // 정지당한 유저
            if (userVo.getSuspendedYn() == 'y' && userVo.getSuspendedExpiry().after(now)) {
                userVo.setResult(UserVo.UserVoResult.SUSPENDED_USER);
            }
            else {
                // 정지 만료 기간이 지났거나 정지당하지 않은 유저
                userVo.setResult(UserVo.UserVoResult.SUCCESS);
            }
        } else {
            // 비밀번호가 틀린 경우
            userVo.setResult(UserVo.UserVoResult.FAILURE);
        }
        return userVo;
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

    public boolean updateUserProfile(int userSeq, String nickname, String url) {
        return userMapper.updateProfile(userSeq, nickname, url) == 1;
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

    public void setPreference(int userSeq, ArrayList<Integer> placeArray) {
        ArrayList<Integer> tagArray = userMapper.selectPlaceTagAll();
        for (int tagSeq : tagArray) {
            userMapper.insertUserFavor(userSeq, tagSeq);
        }
        for (int placeSeq : placeArray) {
            ArrayList<RecommendTagVo> recommendTagVos = userMapper.selectTagByPlaceSeq(placeSeq);
            for (RecommendTagVo recommendTagVo : recommendTagVos) {
                int tag = recommendTagVo.getTagSeq();
                userMapper.updateUserFavor(userSeq, tag);
            }
        }
    }

    public LinkedList<Map<String, Object>> sendPreference() {
        ArrayList<TripPlaceDto> tripPlaceDtos = this.userMapper.selectPreferencePlace();
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        for (TripPlaceDto tripPlaceDto : tripPlaceDtos) {
            Map<String, Object> map = new HashMap<>();
            map.put("place_seq", tripPlaceDto.getTripPlaceSeq());
            map.put("place_name", tripPlaceDto.getPlaceName());
            map.put("image_url", tripPlaceDto.getImageUrl());
            result.add(map);
        }
        return result;
    }

    public Map<String, Object> badgeGet(int userSeq) {
        UserDto userDto = userMapper.selectByUserSeq(userSeq);
        if (userDto == null) { return null; }
        ArrayList<Map<String, Object>> badges = userMapper.selectBadgeByUserSeq(userSeq);
        Map<String, Object> badgeList = new HashMap<>();
        String[] regions = {"seoul", "incheon", "busan", "daegu", "gwangju", "jeonbuk", "daejeon", "chungbuk", "gangwon", "jeju"};
        int cnt = 0;
        int total = 0;
        for(String region:regions) {
            if (cnt < badges.size() && Objects.equals((String) badges.get(cnt).get("region_name"), region)) {
                badgeList.put(region, badges.get(cnt).get("count"));
                total += (int) badges.get(cnt).get("count");
                cnt++;
            }
            else {
                badgeList.put(region, 0);
            }
        }
        badgeList.put("total", total);
        return badgeList;
    }
}
