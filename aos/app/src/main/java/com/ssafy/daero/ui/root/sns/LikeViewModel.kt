package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.LikeItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL

class LikeViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    private val _likeUsers = MutableLiveData<PagingData<LikeItem>>()
    val likeUsers : LiveData<PagingData<LikeItem>>
        get() = _likeUsers

    val likeUsersState = MutableLiveData<Int>()

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