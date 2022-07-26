package com.ssafy.daero.utils.preference

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.daero.utils.constant.DAERO
import com.ssafy.daero.utils.constant.JWT

class PreferenceUtil(context: Context) {
    private val prefs : SharedPreferences = context.getSharedPreferences(DAERO, Context.MODE_PRIVATE)

    var jwt: String?
        get() = prefs.getString(JWT, null)
        set(value) {
            prefs.edit().putString(JWT, value).apply()
        }
}