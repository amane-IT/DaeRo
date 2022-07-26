package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.login.*
import com.ssafy.daero.data.dto.signup.*
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
    fun jwtLogin() : Single<Response<JwtLoginResponseDto>>

    @GET("users/{email_address}")
    fun findID(@Path("email_address") email: String): Single<FindIDResponseDto>

    @PUT("users/{user_seq}/profile")
    fun editProfile(
        @Path("user_seq") userSeq: Int,
        @Body profileEditRequest: ProfileEditRequest
    ): Single<Unit>

    @POST("users/reset-password")
    fun findPassword(@Body loginRequestDto: FindPasswordRequestDto): Single<FindPasswordResponseDto>

    @GET("users/{user_email}/reset-password")
    fun emailCheck(@Path("user_email") email: String): Single<FindPasswordCheckEmailResponseDto>

    @POST("users/{password_reset_seq}/reset-password")
    fun findPasswordModify(
        @Body findPasswordModifyRequestDto: FindPasswordModifyRequestDto,
        @Path("password_reset_seq") reset_seq: String
    ): Single<FindPasswordModifyResponseDto>

    @POST("/users/email")
    fun verifyEmail(@Body signupEmailRequestDto: SignupEmailRequestDto): Single<SignupEmailResponseDto>

    @GET("/users/{user_seq}/verified")
    fun verifyUserEmail(@Path("user_seq") user_seq: Int): Single<VerifyUserEmailResponseDto>

    @POST("/users/nickname")
    fun verifyNickname(@Body signupNicknameRequestDto: SignupNicknameRequestDto): Single<SignupNicknameResponseDto>
}