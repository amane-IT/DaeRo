package com.ssafy.daero.utils.constant

enum class FragmentType(val tag: String) {
    HomeFragment(HOME_FRAGMENT),
    SearchFragment(SEARCH_FRAGMENT),
    TripFragment(TRIP_FRAGMENT),
    TravelingFragment(TRAVELING_FRAGMENT),
    TripVerificationFragment(TRIP_VERIFICATION_FRAGMENT),
    TripNextFragment(TRIP_NEXT_FRAGMENT),
    TripFollowFragment(TRIP_FOLLOW_FRAGMENT),
    CollectionFragment(COLLECTION_FRAGMENT),
    MyPageFragment(MY_PAGE_FRAGMENT),
}



const val HOME_FRAGMENT = "home_fragment"
const val SEARCH_FRAGMENT = "search_fragment"
const val TRIP_FRAGMENT = "trip_fragment"   // 여행 전
const val TRAVELING_FRAGMENT = "traveling_fragment" // 여행 중
const val TRIP_VERIFICATION_FRAGMENT = "trip_verification_fragment" // 인증 완료
const val TRIP_NEXT_FRAGMENT = "trip_next_fragment" // 트립스탬프 완료
const val TRIP_FOLLOW_FRAGMENT = "trip_follow_fragment" // 따라가기
const val COLLECTION_FRAGMENT = "collection_fragment"
const val MY_PAGE_FRAGMENT = "my_age_fragment"





const val COMMENT_BOTTOM_SHEET = "comment_bottom_sheet"
const val LIKE_BOTTOM_SHEET = "like_bottom_sheet"
const val REPORT_BOTTOM_SHEET = "report_bottom_sheet"
const val ARTICLE_MENU_BOTTOM_SHEET = "article_menu_bottom_sheet"
const val TRIP_STAMP_BOTTOM_SHEET = "trip_stamp_bottom_sheet"
const val PROFILE_REMOVE_BOTTOM_SHEET = "profile_remove_bottom_sheet"
const val TRIP_COMPLETE_BOTTOM_SHEET =  "trip_complete_bottom_sheet"
const val TRIP_FOLLOW_BOTTOM_SHEET = "trip_follow_bottom_sheet"
