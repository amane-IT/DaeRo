package com.ssafy.daero.ui.root.mypage

interface FollowListener {
    fun follow(userSeq: Int)
    fun unFollow(userSeq: Int)
}