package com.ssafy.daero.utils

import com.ssafy.daero.data.dto.trip.TripAlbumResponseDto
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

val myAlbums = listOf(
    TripAlbumResponseDto(trip_seq = 1, image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행", expose = 'Y', like_yn = 'N'),
    TripAlbumResponseDto(trip_seq = 1, image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행", expose = 'Y', like_yn = 'N'),
    TripAlbumResponseDto(trip_seq = 1, image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행", expose = 'Y', like_yn = 'N'),
    TripAlbumResponseDto(trip_seq = 1, image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행", expose = 'Y', like_yn = 'N'),
    TripAlbumResponseDto(trip_seq = 1, image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행", expose = 'Y', like_yn = 'N'),
    TripAlbumResponseDto(trip_seq = 1, image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행", expose = 'Y', like_yn = 'N'),
    TripAlbumResponseDto(trip_seq = 1, image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행", expose = 'Y', like_yn = 'N'),
    TripAlbumResponseDto(trip_seq = 1, image_url = "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920",
        title = "강릉 여행", expose = 'Y', like_yn = 'N')
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