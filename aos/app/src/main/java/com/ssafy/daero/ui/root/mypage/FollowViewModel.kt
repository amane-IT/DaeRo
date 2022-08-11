package com.ssafy.daero.ui.root.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.user.FollowResponseDto
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class FollowViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()

    private val _follow = MutableLiveData< PagingData<FollowResponseDto>>()
    val follow : LiveData<PagingData<FollowResponseDto>>
        get() = _follow

    fun follow(userSeq: Int) {

        addDisposable(
            snsRepository.follow(userSeq)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("FollowVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun unFollow(userSeq: Int) {

        addDisposable(
            snsRepository.unFollow(userSeq)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("FollowVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun follower(userSeq: Int) {

        addDisposable(
            snsRepository.follower(userSeq).cachedIn(viewModelScope)
                .subscribe({
                    _follow.postValue(it)
                }, { throwable ->
                    Log.d("FollowerVM_DaeRo", throwable.toString())
                })
        )
    }

    fun following(userSeq: Int) {

        addDisposable(
            snsRepository.following(userSeq).cachedIn(viewModelScope)
                .subscribe({
                    _follow.postValue(it)
                }, { throwable ->
                    Log.d("FollowingVM_DaeRo", throwable.toString())
                })
        )
    }
}