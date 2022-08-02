package com.ssafy.daero.utils

import androidx.paging.PagingData
import com.ssafy.daero.data.dto.article.ArticleHomeItem
import com.ssafy.daero.data.dto.search.ArticleItem
import com.ssafy.daero.data.dto.search.ArticleMoreItem
import com.ssafy.daero.data.dto.search.UserNameItem
import com.ssafy.daero.data.dto.trip.TripHotResponseDto
import com.ssafy.daero.data.dto.trip.TripInformationResponseDto
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto

val popularTripPlaces = listOf(
    TripPopularResponseDto(
        trip_place_seq = 0,
        image_url = "https://unsplash.com/photos/A5rCN8626Ck/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8Mnx8dHJpcHxlbnwwfHx8fDE2NTg5NzU2OTQ&force=true&w=1920",
        place_name = "강릉"
    ),
    TripPopularResponseDto(
        trip_place_seq = 0,
        image_url = "https://unsplash.com/photos/DlF0mqHKvkg/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MTF8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Njk0&force=true&w=1920",
        place_name = "강릉"
    ),
    TripPopularResponseDto(
        trip_place_seq = 0,
        image_url = "https://unsplash.com/photos/Y8lhl6j_OUU/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MTh8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Njk0&force=true&w=1920",
        place_name = "강릉"
    ),
    TripPopularResponseDto(
        trip_place_seq = 0,
        image_url = "https://unsplash.com/photos/_g1WdcKcV3w/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjF8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Nzkz&force=true&w=1920",
        place_name = "강릉"
    ),
    TripPopularResponseDto(
        trip_place_seq = 0,
        image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        place_name = "강릉"
    ),
    TripPopularResponseDto(
        trip_place_seq = 0,
        image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        place_name = "강릉"
    ),
    TripPopularResponseDto(
        trip_place_seq = 0,
        image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        place_name = "강릉"
    ),
    TripPopularResponseDto(
        trip_place_seq = 0,
        image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        place_name = "강릉"
    )
)

val hotArticles = listOf(
    TripHotResponseDto(
        article_seq = 0,
        thumbnail_url = "https://unsplash.com/photos/_g1WdcKcV3w/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjF8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Nzkz&force=true&w=1920",
        title = "강릉 여행"
    ),
    TripHotResponseDto(
        article_seq = 0,
        thumbnail_url = "https://unsplash.com/photos/DlF0mqHKvkg/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MTF8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Njk0&force=true&w=1920",
        title = "강릉 여행"
    ),
    TripHotResponseDto(
        article_seq = 0,
        thumbnail_url = "https://unsplash.com/photos/A5rCN8626Ck/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8Mnx8dHJpcHxlbnwwfHx8fDE2NTg5NzU2OTQ&force=true&w=1920",
        title = "강릉 여행"
    ),
    TripHotResponseDto(
        article_seq = 0,
        thumbnail_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행"
    ),
    TripHotResponseDto(
        article_seq = 0,
        thumbnail_url = "https://unsplash.com/photos/_g1WdcKcV3w/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjF8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Nzkz&force=true&w=1920",
        title = "강릉 여행"
    ),
)

val tripInfo = TripInformationResponseDto(
    address = "강원 강릉시 창해로14번길 20-1",
    description = "강릉 안목해변은 아름다운 커피거리로 전국에서도 손꼽히는 곳이다.가을에는 커피 축제를 열고 해안선이 쭉 이어져 있어 긴 해안선을 따라 드라이브를 하면 여러 관광지까지 함께 볼 수 있다. 특히나 강릉 커피 거리에서 유명한 몇몇 커피집을 가면 저마다 독특한 커피의 향과 맛을 느낄 수 있을 뿐만 아니라 가게마다 자랑하는 디저트들을 맛보는 것 또한 즐겁다.",
    image_url = "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
    latitude = 37.462192,
    longitude = 128.565246,
    place_name = "강릉 안목해변",
    place_seq = 1
)

val searchedUser = listOf(
    UserNameItem(
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "김싸피",
        1
    ),
    UserNameItem(
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "김싸피",
        1
    ),
    UserNameItem(
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "김싸피",
        1
    ),
    UserNameItem(
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "김싸피",
        1
    )
)

val pagingUser = PagingData.from(searchedUser)

val articles = listOf(
    ArticleHomeItem(
        article_seq = 3,
        comments = 50,
        created_at = "2022-07-23",
        description = "이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구",
        end_date = "2022-07-23",
        likes = 123,
        nickname = "김싸피",
        profile_url = "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        start_date = "2022-07-23",
        thumbnail_url = "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        title = "강릉 ~ 속초 여행",
        user_seq = 3
    ),
    ArticleHomeItem(
        article_seq = 3,
        comments = 0,
        created_at = "2022-07-23",
        description = "어쩌구 저쩌구",
        end_date = "2022-07-23",
        likes = 0,
        nickname = "김싸피",
        profile_url = "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        start_date = "2022-07-23",
        thumbnail_url = "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        title = "강릉 ~ 속초 여행",
        user_seq = 3
    ),
    ArticleHomeItem(
        article_seq = 3,
        comments = 50,
        created_at = "2022-07-23",
        description = "이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구",
        end_date = "2022-07-23",
        likes = 123,
        nickname = "김싸피",
        profile_url = "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        start_date = "2022-07-23",
        thumbnail_url = "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        title = "강릉 ~ 속초 여행",
        user_seq = 3
    ),
    ArticleHomeItem(
        article_seq = 3,
        comments = 50,
        created_at = "2022-07-23",
        description = "이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구이번 여행은 어쩌구 저쩌구",
        end_date = "2022-07-23",
        likes = 123,
        nickname = "김싸피",
        profile_url = "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        start_date = "2022-07-23",
        thumbnail_url = "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        title = "강릉 ~ 속초 여행",
        user_seq = 3
    )
)

val searchedArticleContent = listOf(
    ArticleItem(1,
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "강원도"
        ),
    ArticleItem(1,
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "강원도"
    ),
    ArticleItem(1,
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "강원도"
    ),
    ArticleItem(1,
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "강원도"
    ),
)

val searchedArticlePlace = listOf(
    ArticleItem(1,
        "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        "강원도"
    ),
    ArticleItem(1,
        "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        "강원도"
    ),
    ArticleItem(1,
        "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        "강원도"
    ),
    ArticleItem(1,
        "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        "강원도"
    ),
)

val searchedArticleContentMore = PagingData.from(
    listOf(
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        ),
        ArticleMoreItem(
            1,
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "2022.08.02",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            "강원도 여행",
            "2022.07.30",
            "2022.08.01",
            15,
            20,
        )
    )
)