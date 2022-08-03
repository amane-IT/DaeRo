package com.ssafy.daero.utils.preference

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.daero.utils.constant.*

class PreferenceUtil(context: Context) {
    private val prefs : SharedPreferences = context.getSharedPreferences(DAERO, Context.MODE_PRIVATE)

    var jwt: String?
        get() = prefs.getString(JWT, null)
        set(value) {
            prefs.edit().putString(JWT, value).apply()
        }

    var userSeq: Int
        get() = prefs.getInt(USER_SEQ, 0)
        set(value) {
            prefs.edit().putInt(USER_SEQ, value).apply()
        }

    var nickname: String?
        get() = prefs.getString(NICKNAME, null)
        set(value) {
            prefs.edit().putString(NICKNAME, value).apply()
        }

    // 여행 상태
    var tripState : Int
        get() = prefs.getInt(TRIP_STATE, TRIP_BEFORE)
        set(value) {
            prefs.edit().putInt(TRIP_STATE, value).apply()
        }

    // 따라가기 여부
    var isFollow : Boolean
        get() = prefs.getBoolean(IS_FOLLOW, false)
        set(value) {
            prefs.edit().putBoolean(IS_FOLLOW, value)
        }

    // 작성중 여부
    var isPosting : Boolean
        get() = prefs.getBoolean(IS_POSTING, false)
        set(value) {
            prefs.edit().putBoolean(IS_POSTING, value)
        }

    var ftoken: String?
        get() = prefs.getString(FCM_TOKEN, null)
        set(value) {
            prefs.edit().putString(FCM_TOKEN, value).apply()
        }

    fun initAll() {
        jwt = null
        userSeq = 0
        nickname = null
    }
}