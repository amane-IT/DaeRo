package com.ssafy.daero.core.network

import com.ssafy.daero.application.App
import okhttp3.Interceptor
import okhttp3.Response

class JWTInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("jwt", App.prefs.jwt ?: "").build()
        return chain.proceed(request)
    }
}