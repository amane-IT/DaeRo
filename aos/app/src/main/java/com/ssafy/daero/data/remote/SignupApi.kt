package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.signup.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SignupApi {
    @POST("/users/email")
    fun verifyEmail(@Body signupEmailRequestDto: SignupEmailRequestDto) : Single<SignupEmailResponseDto>

    @GET("/users/{user_seq}/verified")
    fun verifyUserEmail(@Path("user_seq") user_seq: Int): Single<VerifyUserEmailResponseDto>

    @POST("/users/nickname")
    fun verifyNickname(@Body signupNicknameRequestDto: SignupNicknameRequestDto) : Single<SignupNicknameResponseDto>
}