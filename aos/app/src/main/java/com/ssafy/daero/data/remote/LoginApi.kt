package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.login.FindIDResponseDto
import com.ssafy.daero.data.dto.login.LoginRequestDto
import com.ssafy.daero.data.dto.login.LoginResponseDto
import com.ssafy.daero.data.dto.login.ProfileEditRequest
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface LoginApi {
    @POST("login")
    fun emailLogin(@Body loginRequestDto: LoginRequestDto) : Single<LoginResponseDto>

    @GET("users/{email_address}")
    fun findID(@Path("email_address") email: String): Single<FindIDResponseDto>

    @PUT("users/{user_seq}/profile")
    fun editProfile(@Path("user_seq") userSeq : Int, @Body profileEditRequest: ProfileEditRequest) : Single<Void>
}