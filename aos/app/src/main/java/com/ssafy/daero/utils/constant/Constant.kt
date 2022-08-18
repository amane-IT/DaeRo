package com.ssafy.daero.utils.constant


/**
 * Bundle Key Constant
 */
const val PLACE_SEQ = "trip_seq"
const val CATEGORY_TAGS = "category_tags"
const val REGION_TAGS = "region_tags"
const val TAG_COLLECTION = "tag_collection"
const val ARTICLE_SEQ = "article_seq"
const val IS_RECOMMEND = "is_recommend"
const val IS_RE_RECOMMEND = "is_re_recommend"
const val IS_TRIP_STAMP_UPDATE = "is_trip_stamp_update"
const val TRIP_STAMP_ID = "trip_stamp_id"

/**
 * 신고 유형
 */
const val ARTICLE = 90
const val COMMENT = 91
const val USER = 92


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
const val NOTIFICATION = "t_notification"