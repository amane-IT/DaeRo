package com.ssafy.daero.user.mapper;

import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.trip.vo.RecommendTagVo;
import com.ssafy.daero.user.dto.EmailVerificationDto;
import com.ssafy.daero.user.dto.PasswordResetDto;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.vo.ChangePasswordVo;
import com.ssafy.daero.user.vo.SignupVo;
import com.ssafy.daero.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue")
@Mapper
public interface UserMapper {
    int insertUser(String userEmail);

    int insertPasswordResetKey(PasswordResetDto passwordResetDto);

    int insertEmailVerificationKey(EmailVerificationDto emailVerificationDto);

    int insertUserFavor(@Param("userSeq") int userSeq, @Param("tag") int tag);

    UserVo selectById(String id);

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

    ArrayList<Integer> selectPlaceTagAll();

    ArrayList<RecommendTagVo> selectTagByPlaceSeq(int placeSeq);

    ArrayList<TripPlaceDto> selectPreferencePlace();

    int updateUser(SignupVo signupVo);

    int updateUserPassword(ChangePasswordVo changePasswordVo);

    int updateUserFavor(@Param("userSeq") int userSeq, @Param("tagSeq") int tagSeq);

    // 유저 프로필 수정
    int updateProfile(@Param("user_seq") int user_seq, @Param("nickname") String nickname, @Param("profileUrl") String url);

    // 회원 탈퇴
    int deleteUser(int user_seq);

    ArrayList<Map<String, Object>> selectBadgeByUserSeq(int userSeq);
}
