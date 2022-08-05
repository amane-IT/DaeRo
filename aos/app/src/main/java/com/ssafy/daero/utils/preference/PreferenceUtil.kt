package com.ssafy.daero.utils.preference

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.daero.utils.constant.*

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(DAERO, Context.MODE_PRIVATE)

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

    var ftoken: String?
        get() = prefs.getString(FCM_TOKEN, null)
        set(value) {
            prefs.edit().putString(FCM_TOKEN, value).apply()
        }

    // 현재 여행중인 여행지 seq
    var curPlaceSeq: Int
        get() = prefs.getInt(CUR_TRIP_SEQ, 0)
        set(value) {
            prefs.edit().putInt(CUR_TRIP_SEQ, value).apply()
        }

    // 여행 상세페이지에서 여행 시작 버튼 클릭 여부
    var isTripStart: Boolean
        get() = prefs.getBoolean(IS_TRIP_START, false)
        set(value) {
            prefs.edit().putBoolean(IS_TRIP_START, value).apply()
        }

    // 여행 상태
    var tripState: Int
        get() = prefs.getInt(TRIP_STATE, TRIP_BEFORE)
        set(value) {
            prefs.edit().putInt(TRIP_STATE, value).apply()
        }

    // 따라가기 여부
    var isFollow: Boolean
        get() = prefs.getBoolean(IS_FOLLOW, false)
        set(value) {
            prefs.edit().putBoolean(IS_FOLLOW, value).apply()
        }

    // 작성중 여부
    var isPosting: Boolean
        get() = prefs.getBoolean(IS_POSTING, false)
        set(value) {
            prefs.edit().putBoolean(IS_POSTING, value).apply()
        }

    // 최근 여행지 인증 완료 시간
    var verificationTime: Long
        get() = prefs.getLong(VERIFICATION_TIME, 0L)
        set(value) {
            prefs.edit().putLong(VERIFICATION_TIME, value).apply()
        }

    // 트립스탬프 완료 여부
    var isTripStampComplete: Boolean
        get() = prefs.getBoolean(IS_TRIP_STAMP_COMPLETE, false)
        set(value) {
            prefs.edit().putBoolean(IS_TRIP_STAMP_COMPLETE, value).apply()
        }

    // 처음 여행인지 여부
    var isFirstTrip: Boolean
        get() = prefs.getBoolean(IS_FIRST_TRIP, true)
        set(value) {
            prefs.edit().putBoolean(IS_FIRST_TRIP, value).apply()
        }

    // 다음 여행지 옵션: 도보 or 차량
    var tripTransportation: String
        get() = prefs.getString(TRIP_TRANSPORTATION, "walk") ?: "walk"
        set(value) {
            prefs.edit().putString(TRIP_TRANSPORTATION, value).apply()
        }

    // 다음 여행지 옵션: 시간
    var tripTime: Int
        get() = prefs.getInt(TRIP_TIME, 30)
        set(value) {
            prefs.edit().putInt(TRIP_TIME, value).apply()
        }

    fun initUser() {
        jwt = null
        userSeq = 0
        nickname = null
    }

    fun initTrip() {
        curPlaceSeq = 0
        isTripStart = false
        isFollow = false
        isPosting = false
        verificationTime = 0L
        isTripStampComplete = false
        isFirstTrip = true
        tripTransportation = "walk"
        tripTime = 30
    }
}