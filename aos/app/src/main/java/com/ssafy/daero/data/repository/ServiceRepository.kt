package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.data.dto.login.*
import com.ssafy.daero.data.dto.signup.*
import com.ssafy.daero.data.remote.ServiceApi
import com.ssafy.daero.data.remote.UserApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ServiceRepository private constructor(context: Context) {

    // Service API
    private val serviceApi = RetrofitBuilder.retrofit.create(ServiceApi::class.java)

    companion object {
        private var instance: ServiceRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = ServiceRepository(context)
            }
        }

        fun get(): ServiceRepository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }

}