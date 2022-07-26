package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.login.FindIDResponseDto
import com.ssafy.daero.data.dto.login.LoginRequestDto
import com.ssafy.daero.data.dto.login.LoginResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginApi {
    @POST("login")
    fun emailLogin(@Body loginRequestDto: LoginRequestDto) : Single<LoginResponseDto>

    @GET("users/{email_address}")
    fun findID(@Path("email_address") email: String): Single<FindIDResponseDto>
}