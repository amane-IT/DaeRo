package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.data.dto.signup.email.SignupEmailRequestDto
import com.ssafy.daero.data.dto.signup.email.SignupEmailResponseDto
import com.ssafy.daero.data.dto.signup.email.VerifyUserEmailResponseDto
import com.ssafy.daero.data.dto.nickname.SignupNicknameRequestDto
import com.ssafy.daero.data.dto.nickname.SignupNicknameResponseDto
import com.ssafy.daero.data.remote.SignupApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.IllegalStateException

class SignupRepository private constructor(context: Context){

    private val signupApi = RetrofitBuilder.retrofit.create(SignupApi::class.java)

    fun verifyEmail(signupEmailRequestDto: SignupEmailRequestDto) : Single<SignupEmailResponseDto>{
        return signupApi.verifyEmail(signupEmailRequestDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verifyUserEmail(signupEmailResponseDto: SignupEmailResponseDto) : Single<VerifyUserEmailResponseDto>{
        return signupApi.verifyUserEmail(signupEmailResponseDto.userSeq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verifyNickname(signupNicknameRequestDto: SignupNicknameRequestDto) : Single<SignupNicknameResponseDto>{
        return signupApi.verifyNickname(signupNicknameRequestDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object{
        private var instance : SignupRepository? = null

        fun initialize(context: Context){
            if(instance == null){
                instance = SignupRepository(context)
            }
        }

        fun get(): SignupRepository{
            return instance ?: throw IllegalStateException("Respository must be initialize")
        }
    }
}