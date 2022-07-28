package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.login.*
import com.ssafy.daero.data.dto.resetPassword.ResetPasswordRequestDto
import com.ssafy.daero.data.dto.resetPassword.ResetPasswordResponseDto
import com.ssafy.daero.data.dto.signup.*
import com.ssafy.daero.data.dto.user.ProfileEditRequestDto
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    /**
     * 로그인
     */
    @POST("users/login")
    fun emailLogin(@Body loginRequestDto: LoginRequestDto): Single<Response<LoginResponseDto>>

    /**
     * jwt 로그인
     */
    @POST("users/login-jwt")
    fun jwtLogin(): Single<Response<JwtLoginResponseDto>>

    /**
     * 가입 여부 확인
     */
    @GET("users/{email_address}")
    fun findID(@Path("email_address") email: String): Single<Response<FindIDResponseDto>>

    /**
     * 유저 프로필 조회
     */
    @GET("users/{user_seq}/profile")
    fun getUserProfile(@Path("user_seq") userSeq: Int): Single<Response<UserProfileResponseDto>>

    /**
     * 유저 프로필 수정
     */
    @PUT("users/{user_seq}/profile")
    fun editProfile(
        @Path("user_seq") userSeq: Int,
        @Body profileEditRequestDto: ProfileEditRequestDto
    ): Completable

    /**
     * 비밀번호 찾기 요청
     */
    @POST("users/reset-password")
    fun findPassword(@Body loginRequestDto: FindPasswordRequestDto): Single<Response<FindPasswordResponseDto>>

    /**
     * 비밀번호 찾기 성공 여부
     */
    @GET("users/{user_email}/reset-password")
    fun emailCheck(@Path("user_email") email: String): Single<Response<FindPasswordCheckEmailResponseDto>>

    /**
     * 재설정 비밀번호 요청
     */
    @POST("users/{password_reset_seq}/reset-password")
    fun findPasswordModify(
        @Body findPasswordModifyRequestDto: FindPasswordModifyRequestDto,
        @Path("password_reset_seq") reset_seq: String
    ): Single<Response<FindPasswordModifyResponseDto>>


    /**
     * 이메일 인증 요청
     */
    @POST("/users/email")
    fun verifyEmail(@Body signupEmailRequestDto: SignupEmailRequestDto): Single<Response<SignupEmailResponseDto>>

    /**
     * 이메일 인증 여부
     */
    @GET("/users/{user_seq}/verified")
    fun verifyUserEmail(@Path("user_seq") user_seq: Int): Single<Response<VerifyUserEmailResponseDto>>

    /**
     * 닉네임 중복 검사
     */
    @POST("/users/nickname")
    fun verifyNickname(@Body signupNicknameRequestDto: SignupNicknameRequestDto): Single<Response<SignupNicknameResponseDto>>

    /**
     * 회원가입
     */
    @POST("users/signup")
    fun signup(@Body signupRequestDto: SignupRequestDto): Single<Void>

    /**
     * 선호도 조사 정보 요청
     */
    @GET("users/{user_seq}/preference")
    fun getPreferences(@Path("user_seq") userSeq: Int): Single<Response<List<TripPreferenceResponseDto>>>

    /**
    선호도 조사 결과 전송
     * */
    @POST("users/{user_seq}/preference")
    fun postPreference(
        @Path("user_seq") userSeq: Int,
        @Body preferenceList: List<Int>
    ): Single<Void>

    /**
     * 비밀번호 확인 요청
     */
    @POST("users/{user_seq}/password")
    fun confirmPassword(
        @Path("user_seq") userSeq: Int,
        @Body resetPasswordRequestDto: ResetPasswordRequestDto
    ): Single<Response<ResetPasswordResponseDto>>

    /**
     * 비밀번호 변경 요청
     */
    @PUT("users/{user_seq}/password")
    fun updatePassword(
        @Path("user_seq") userSeq: Int,
        @Body resetPasswordRequestDto: ResetPasswordRequestDto
    ): Single<Response<ResetPasswordResponseDto>>

}