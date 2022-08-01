package com.ssafy.daero.ui.root.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.sns.UserNameItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL

class SearchViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState_userName = MutableLiveData<Int>()

    private val _resultUserSearch = MutableLiveData<PagingData<UserNameItem>>()
    val resultUserSearch: LiveData<PagingData<UserNameItem>>
        get() = _resultUserSearch

    fun searchUserName(searchKeyWord: String){
        addDisposable(
            snsRepository.searchUserName(searchKeyWord).cachedIn(viewModelScope)
                .subscribe({
                    Log.d("SearchVM_DaeRo", "searchUserName: $it")
                    _resultUserSearch.postValue(it)
                }, { throwable ->
                    Log.d("SearchVM_DaeRo", "searchUserName: ${throwable.toString()}")
                    responseState_userName.postValue(FAIL)
                })
        )
    }

    fun searchArticle(){

    }
}