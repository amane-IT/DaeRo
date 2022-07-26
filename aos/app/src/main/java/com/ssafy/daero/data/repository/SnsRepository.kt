package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.data.dto.login.*
import com.ssafy.daero.data.dto.signup.*
import com.ssafy.daero.data.remote.SnsApi
import com.ssafy.daero.data.remote.UserApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SnsRepository private constructor(context: Context) {

    // Sns API
    private val snsApi = RetrofitBuilder.retrofit.create(SnsApi::class.java)

    companion object {
        private var instance: SnsRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = SnsRepository(context)
            }
        }

        fun get(): SnsRepository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }

}