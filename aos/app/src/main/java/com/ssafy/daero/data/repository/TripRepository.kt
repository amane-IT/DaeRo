package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.data.dto.login.*
import com.ssafy.daero.data.dto.signup.*
import com.ssafy.daero.data.remote.TripApi
import com.ssafy.daero.data.remote.UserApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TripRepository private constructor(context: Context) {

    // Trip API
    private val tripApi = RetrofitBuilder.retrofit.create(TripApi::class.java)

    companion object {
        private var instance: TripRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = TripRepository(context)
            }
        }

        fun get(): TripRepository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }

}