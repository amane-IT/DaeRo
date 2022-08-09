package com.ssafy.daero.utils

import androidx.paging.PagingData
import com.ssafy.daero.data.dto.collection.CollectionItem
import com.ssafy.daero.data.dto.search.ArticleItem
import com.ssafy.daero.data.dto.search.UserNameItem
import com.ssafy.daero.data.dto.trip.TripHotResponseDto
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
        image_url = "https://unsplash.com/photos/_g1WdcKcV3w/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjF8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Nzkz&force=true&w=1920",
        title = "강릉 여행"
    ),
    TripHotResponseDto(
        article_seq = 0,
        image_url = "https://unsplash.com/photos/DlF0mqHKvkg/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MTF8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Njk0&force=true&w=1920",
        title = "강릉 여행"
    ),
    TripHotResponseDto(
        article_seq = 0,
        image_url = "https://unsplash.com/photos/A5rCN8626Ck/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8Mnx8dHJpcHxlbnwwfHx8fDE2NTg5NzU2OTQ&force=true&w=1920",
        title = "강릉 여행"
    ),
    TripHotResponseDto(
        article_seq = 0,
        image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행"
    ),
    TripHotResponseDto(
        article_seq = 0,
        image_url = "https://unsplash.com/photos/_g1WdcKcV3w/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjF8fHRyaXB8ZW58MHx8fHwxNjU4OTc1Nzkz&force=true&w=1920",
        title = "강릉 여행"
    ),
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


val searchedArticleContent = listOf(
    ArticleItem(
        1,
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "강원도"
    ),
    ArticleItem(
        1,
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "강원도"
    ),
    ArticleItem(
        1,
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "강원도"
    ),
    ArticleItem(
        1,
        "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
        "강원도"
    ),
)

val searchedArticlePlace = listOf(
    ArticleItem(
        1,
        "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        "강원도"
    ),
    ArticleItem(
        1,
        "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        "강원도"
    ),
    ArticleItem(
        1,
        "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        "강원도"
    ),
    ArticleItem(
        1,
        "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
        "강원도"
    ),
)
//
//val searchedArticleContentMore = PagingData.from(
//    listOf(
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        ),
//        ArticleMoreItem(
//            1,
//            "김싸피",
//            1,
//            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
//            "2022.08.02",
//            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
//            "바다 여행",
//            "강원도 여행",
//            "2022.07.30",
//            "2022.08.01",
//            15,
//            20,
//        )
//    )
//)

val collections = PagingData.from(
    listOf(
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
        CollectionItem(
            "김싸피",
            1,
            "https://images.unsplash.com/photo-1503249023995-51b0f3778ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&q=80",
            "https://unsplash.com/photos/odII8BzuWU8/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8NDJ8fGJlYWNofGVufDB8fHx8MTY1OTA3Mjc4MA&force=true&w=1920",
            "바다 여행",
            20,
        ),
    )
)