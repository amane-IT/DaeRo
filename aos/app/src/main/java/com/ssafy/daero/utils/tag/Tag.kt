package com.ssafy.daero.utils.tag

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Tag(
    val seq: Int,
    val tag: String
)

@Parcelize
data class TagCollection(
    val categoryTags : List<Int>,
    val regionTags : List<Int>
) : Parcelable

val categoryTags = listOf(
    Tag(1, "바다"), Tag(2, "산"), Tag(3, "강"),
    Tag(4, "수목원"), Tag(5, "역사"), Tag(6, "공원"),
    Tag(7, "전망대"), Tag(8, "체험"), Tag(9, "먹거리"),
    Tag(10, "거리"), Tag(11, "박물관"), Tag(12, "불교"),
    Tag(13, "성당"), Tag(14, "교회")
)

val regionTags = listOf(
    Tag(1, "서울"), Tag(2, "경기·인천"), Tag(3, "부산·울산·경남"),
    Tag(4, "대구·경북"), Tag(5, "광주·전남"), Tag(6, "전북"),
    Tag(7, "대전·충남"), Tag(8, "충북"), Tag(9, "강원"),
    Tag(10, "제주")
)