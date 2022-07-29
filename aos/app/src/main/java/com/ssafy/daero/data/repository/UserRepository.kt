package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.application.App.Companion.userSeq
import com.ssafy.daero.data.dto.login.*
import com.ssafy.daero.data.dto.resetPassword.ResetPasswordRequestDto
import com.ssafy.daero.data.dto.resetPassword.ResetPasswordResponseDto
import com.ssafy.daero.data.dto.signup.*
import com.ssafy.daero.data.dto.user.ProfileEditRequestDto
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.data.remote.UserApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Path

class UserRepository private constructor(context: Context) {

    // User API
    private val userApi = RetrofitBuilder.retrofit.create(UserApi::class.java)

    fun emailLogin(loginRequestDto: LoginRequestDto): Single<Response<LoginResponseDto>> {
        return userApi.emailLogin(loginRequestDto)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun jwtLogin(): Single<Response<JwtLoginResponseDto>> {
        return userApi.jwtLogin()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun findID(email: String): Single<Response<FindIDResponseDto>> {
        return userApi.findID(email)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserProfile(userSeq: Int): Single<Response<UserProfileResponseDto>> {
        return userApi.getUserProfile(userSeq)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editProfile(userSeq: Int, profileEditRequestDto: ProfileEditRequestDto): Completable {
        return userApi.editProfile(userSeq, profileEditRequestDto)
    }

    fun findPassword(findPasswordRequestDto: FindPasswordRequestDto): Single<Response<FindPasswordResponseDto>> {
        return userApi.findPassword(findPasswordRequestDto)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun emailCheck(email: String): Single<Response<FindPasswordCheckEmailResponseDto>> {
        return userApi.emailCheck(email)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun findPasswordModify(
        findPasswordModifyRequestDto: FindPasswordModifyRequestDto,
        reset_seq: String
    ): Single<Response<FindPasswordModifyResponseDto>> {
        return userApi.findPasswordModify(findPasswordModifyRequestDto, reset_seq)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verifyEmail(signupEmailRequestDto: SignupEmailRequestDto): Single<Response<SignupEmailResponseDto>> {
        return userApi.verifyEmail(signupEmailRequestDto)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verifyUserEmail(signupEmailResponseDto: SignupEmailResponseDto): Single<Response<VerifyUserEmailResponseDto>> {
        return userApi.verifyUserEmail(signupEmailResponseDto.user_seq)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verifyNickname(signupNicknameRequestDto: SignupNicknameRequestDto): Single<Response<SignupNicknameResponseDto>> {
        return userApi.verifyNickname(signupNicknameRequestDto)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun signup(signupRequestDto: SignupRequestDto): Single<Void> {
        return userApi.signup(signupRequestDto)
    }

    fun getPreference(userSeq: Int): Single<Response<List<TripPreferenceResponseDto>>> {
        return userApi.getPreferences(userSeq)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun postPreference(userSeq: Int, result: List<Int>): Single<Void> {
        return userApi.postPreference(userSeq, result)
    }

    fun confirmPassword(
        userSeq: Int,
        passwordRequestDto: ResetPasswordRequestDto
    ): Single<Response<ResetPasswordResponseDto>> {
        return userApi.confirmPassword(userSeq, passwordRequestDto)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updatePassword(
        userSeq: Int,
        passwordRequestDto: ResetPasswordRequestDto
    ): Single<Response<ResetPasswordResponseDto>> {
        return userApi.updatePassword(userSeq, passwordRequestDto)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun withdrawal(userSeq: Int): Single<Response<Boolean>> {
        return userApi.withdrawal(userSeq)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
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