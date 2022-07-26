package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.data.dto.login.*
import com.ssafy.daero.data.dto.signup.*
import com.ssafy.daero.data.remote.UserApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRepository private constructor(context: Context) {

    // User API
    private val userApi = RetrofitBuilder.retrofit.create(UserApi::class.java)

    fun emailLogin(loginRequestDto: LoginRequestDto): Single<LoginResponseDto> {
        return userApi.emailLogin(loginRequestDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun findID(email: String): Single<FindIDResponseDto> {
        return userApi.findID(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editProfile(userSeq: Int, profileEditRequest: ProfileEditRequest): Single<Void> {
        return userApi.editProfile(userSeq, profileEditRequest)
    }

    fun findPassword(findPasswordRequestDto: FindPasswordRequestDto): Single<FindPasswordResponseDto> {
        return userApi.findPassword(findPasswordRequestDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun emailCheck(email: String): Single<FindPasswordCheckEmailResponseDto> {
        return userApi.emailCheck(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun findPasswordModify(
        findPasswordModifyRequestDto: FindPasswordModifyRequestDto,
        reset_seq: String
    ): Single<FindPasswordModifyResponseDto> {
        return userApi.findPasswordModify(findPasswordModifyRequestDto, reset_seq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verifyEmail(signupEmailRequestDto: SignupEmailRequestDto) : Single<SignupEmailResponseDto>{
        return userApi.verifyEmail(signupEmailRequestDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verifyUserEmail(signupEmailResponseDto: SignupEmailResponseDto) : Single<VerifyUserEmailResponseDto>{
        return userApi.verifyUserEmail(signupEmailResponseDto.userSeq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verifyNickname(signupNicknameRequestDto: SignupNicknameRequestDto) : Single<SignupNicknameResponseDto>{
        return userApi.verifyNickname(signupNicknameRequestDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun signup(signupRequestDto: SignupRequestDto) : Single<Void>{
        return userApi.signup(signupRequestDto)
    }

    fun getPreference(userSeq: Int): Single<MutableList<TripPreferenceResponseDto>> {
        return userApi.getPreferences(userSeq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        private var instance: UserRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = UserRepository(context)
            }
        }

        fun get(): UserRepository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }

}