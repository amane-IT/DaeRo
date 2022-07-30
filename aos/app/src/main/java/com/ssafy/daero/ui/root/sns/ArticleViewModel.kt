package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.ArticleResponseDto
import com.ssafy.daero.data.dto.article.LikeItem
import com.ssafy.daero.data.dto.trip.TripAlbumItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ArticleViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()
    lateinit var articleData: ArticleResponseDto


    private val _likeUsers = MutableLiveData<PagingData<LikeItem>>()
    val likeUsers : LiveData<PagingData<LikeItem>>
        get() = _likeUsers

    val likeUsersState = MutableLiveData<Int>()

    fun article(articleSeq: Int) {

        addDisposable(
            snsRepository.article(articleSeq)
                .subscribe({ response ->
                    articleData = ArticleResponseDto(response.body()!!.user_seq,
                        response.body()!!.nickname, response.body()!!.profile_url,
                        response.body()!!.title, response.body()!!.trip_comment,
                        response.body()!!.trip_expenses, response.body()!!.rating,
                        response.body()!!.likes, response.body()!!.comments,
                        response.body()!!.tags, response.body()!!.records
                        )
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun getLikeUsers(articleSeq: Int) {
        addDisposable(
            snsRepository.getLikeUsers(articleSeq)
                .cachedIn(viewModelScope)
                .subscribe({ it ->
                    _likeUsers.postValue(it)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    likeUsersState.postValue(FAIL)
                })
        )
    }
}