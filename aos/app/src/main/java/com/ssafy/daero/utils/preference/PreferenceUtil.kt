package com.ssafy.daero.utils.preference

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.daero.utils.constant.DAERO
import com.ssafy.daero.utils.constant.JWT
import com.ssafy.daero.utils.constant.USER_SEQ

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
}