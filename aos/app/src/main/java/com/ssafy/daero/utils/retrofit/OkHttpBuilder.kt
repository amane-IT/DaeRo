package com.ssafy.daero.utils.retrofit

import com.kakao.sdk.network.ApiFactory.loggingInterceptor
import com.ssafy.daero.core.network.JWTInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpBuilder {
//        private val loggingInterceptor = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY   // todo: 추후 릴리즈하면 Level.None 으로 변경
//       }
    val okHttpClient = OkHttpClient.Builder()
        //.addInterceptor(loggingInterceptor)
        .addInterceptor(JWTInterceptor())
        .build()
}