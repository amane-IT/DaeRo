package com.ssafy.daero.data.remote

import com.ssafy.daero.data.dto.login.LoginRequestDto
import com.ssafy.daero.data.dto.login.LoginResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    fun emailLogin(@Body loginRequestDto: LoginRequestDto) : Single<LoginResponseDto>
}