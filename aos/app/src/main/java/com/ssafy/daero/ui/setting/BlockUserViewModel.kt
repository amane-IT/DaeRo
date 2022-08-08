package com.ssafy.daero.ui.setting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.user.UserBlockResponseDto
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class BlockUserViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()
    var userBlockData = listOf<UserBlockResponseDto>()


    fun getBlockUser() {
        addDisposable(
            snsRepository.getBlockUser()
                .subscribe({
                    userBlockData = it.body()!!
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun blockAdd(userSeq: Int) {
        addDisposable(
            snsRepository.blockAdd(userSeq)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun blockDelete(userSeq: Int) {
        addDisposable(
            snsRepository.blockDelete(userSeq)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

}