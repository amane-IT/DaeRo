package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.data.dto.login.FindIDResponseDto
import com.ssafy.daero.data.dto.login.LoginRequestDto
import com.ssafy.daero.data.dto.login.LoginResponseDto
import com.ssafy.daero.data.remote.LoginApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.Body

class LoginRepository private constructor(context : Context) {

    // Unsplash API
    private val loginApi = RetrofitBuilder.retrofit.create(LoginApi::class.java)

    fun emailLogin(loginRequestDto: LoginRequestDto) : Single<LoginResponseDto> {
        return loginApi.emailLogin(loginRequestDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun findID(email: String) : Single<FindIDResponseDto> {
        return loginApi.findID(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        private var instance : LoginRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = LoginRepository(context)
            }
        }

        fun get() : LoginRepository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}