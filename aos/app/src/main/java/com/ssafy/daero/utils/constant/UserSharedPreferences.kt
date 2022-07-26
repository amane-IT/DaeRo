package com.ssafy.daero.utils.constant

import android.content.Context
import android.content.SharedPreferences

class UserSharedPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)

    fun getString(key: String, value: String): String{
        return prefs.getString(key, value).toString()
    }

    fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getUserSeq(value: Int): Int{
        return prefs.getInt("userSeq", value)
    }

    fun setUserSeq(value: Int) {
        prefs.edit().putInt("userSeq", value)
    }
}