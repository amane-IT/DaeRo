package com.ssafy.daero.core.network

import android.util.Log
import com.ssafy.daero.application.App
import okhttp3.Interceptor
import okhttp3.Response

class JWTInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("jwt", App.prefs.jwt ?: "").build()
        Log.d("JWTInterceptor_DaeRo", "jwt: ${App.prefs.jwt ?: ""}")
        return chain.proceed(request)
    }
}