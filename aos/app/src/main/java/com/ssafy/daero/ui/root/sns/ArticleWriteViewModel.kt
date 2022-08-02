package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.*
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ArticleWriteViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    val responseState = MutableLiveData<Int>()
    var articleTripStampData = listOf<TripStamp>()


    fun getTripStamps() {
        addDisposable(
            tripRepository.getTripStamps()
                .subscribe({
                    articleTripStampData = it
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

}