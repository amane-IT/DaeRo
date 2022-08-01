package com.ssafy.daero.utils.constant


/**
 * Bundle Key Constant
 */
const val PLACE_SEQ = "trip_seq"
const val CATEGORY_TAGS = "category_tags"
const val REGION_TAGS = "region_tags"
const val TAG_COLLECTION = "tag_collection"
const val TRIP_KIND = "trip_kind"


/**
 * 여행 추천 상태, (첫 여행, 다음 여행)
 */
const val FIRST_TRIP = 100
const val NEXT_TRIP = 101


/**
 * 신고 유형
 */
const val ARTICLE = 90
const val COMMENT = 91


/**
 * 여행 상태
 */
const val TRIP_BEFORE = 300 // 여행 전
const val TRIP_ING = 301    // 여행 중
const val TRIP_VERIFICATION = 302 // 인증 완료
const val TRIP_COMPLETE = 303 // 트립스탬프 완료


/**
 * Room Table Name
 */
const val DATABASE = "app_database"
const val TRIP_STAMP = "t_trip_stamp"
const val TRIP_FOLLOW = "t_trip_follow"