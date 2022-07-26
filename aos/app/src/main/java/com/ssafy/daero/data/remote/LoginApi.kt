package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.login.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginApi {
    @POST("users/login")
    fun emailLogin(@Body loginRequestDto: LoginRequestDto) : Single<LoginResponseDto>

    @GET("users/{email_address}")
    fun findID(@Path("email_address") email: String): Single<FindIDResponseDto>

    @POST("users/reset-password")
    fun findPassword(@Body loginRequestDto: FindPasswordRequestDto) : Single<FindPasswordResponseDto>

    @GET("users/{user_email}/reset-password")
    fun emailCheck(@Path("user_email") email: String): Single<FindPasswordCheckEmailResponseDto>

    @POST("users/{password_reset_seq}/reset-password")
    fun findPasswordModify(@Body findPasswordModifyRequestDto: FindPasswordModifyRequestDto, @Path("password_reset_seq") reset_seq: String) : Single<FindPasswordModifyResponseDto>
}