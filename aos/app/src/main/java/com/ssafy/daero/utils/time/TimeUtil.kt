package com.ssafy.daero.utils.time

import java.text.SimpleDateFormat
import java.util.*

// 오늘 날짜 Calendar 반환
fun getNowCalendar() = Calendar.getInstance().apply { time = Date() }

fun calendarToString(cal: Calendar) : String {
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH) + 1
    val day = cal.get(Calendar.DAY_OF_MONTH)
    return "${year}-${month}-${day}"
}

fun calendarToStringOnceYearAgo(cal: Calendar) : String {
    val year = cal.get(Calendar.YEAR) - 1
    val month = cal.get(Calendar.MONTH) + 1
    val day = cal.get(Calendar.DAY_OF_MONTH)
    return "${year}-${month}-${day}"
}

fun stringToDate(dateText: String): Date {
    return SimpleDateFormat("yyyy-MM-dd").parse(dateText)
}
