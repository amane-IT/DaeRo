package com.ssafy.daero.user.service;

import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.mapper.UserMapper;
import com.ssafy.daero.user.vo.LoginVo;
import com.ssafy.daero.user.vo.SignupVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean signup(SignupVo signupVo) {
        return userMapper.insertUser(signupVo) == 1;
    }

    public int login(LoginVo loginVO) {
        UserDto currentUser = userMapper.selectById(loginVO.getId());
        // 존재하는 유저고, 비밀번호도 맞고, 이용정지당한 유저가 아니고, 이메일 인증을 완료한 유저
        if (currentUser != null && Objects.equals(currentUser.getPassword(), loginVO.getHashedPassword())
                && currentUser.getSuspendedYn() == 'n' && currentUser.getEmailVerifiedYn() == 'y') {
            return currentUser.getUserSeq();
        } else {
            return 0;
        }
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
        if (profileUser.getEmail() == null) { return null; }
        profile.put("nickname", profileUser.getNickname());
        profile.put("profile_url", profileUser.getProfileImageLink());
        profile.put("follower", userMapper.selectFollowerCountById(userSeq));
        profile.put("following", userMapper.selectFollowingCountById(userSeq));
        profile.put("badge_count", userMapper.selectAllBadgeById(userSeq));
        if (userSeq == currentUserSeq) { profile.put("followYn", "n"); }
        else if (userMapper.selectFollowerByUserSeq(profileUser.getUserSeq(), currentUserSeq) == 1) {
            profile.put("followYn", "y");
        }
        else { profile.put("followYn", "n"); }
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



}
