package com.ssafy.daero.ui.root.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.search.ArticleMoreItem
import com.ssafy.daero.data.dto.search.SearchArticleResponseDto
import com.ssafy.daero.data.dto.search.UserNameItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SearchContentMoreViewModel : BaseViewModel() {
    private val TAG = "SearchContentMoreVM_DaeRo"
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()

    private val _resultContentSearch = MutableLiveData<PagingData<ArticleMoreItem>>()
    val resultContentSearch: LiveData<PagingData<ArticleMoreItem>>
        get() = _resultContentSearch

    fun searchContentMore(searchKeyWord: String){
        addDisposable(
            snsRepository.searchContentMore(searchKeyWord).cachedIn(viewModelScope)
                .subscribe({
                    Log.d(TAG, "searchContentMore: $it")
                    _resultContentSearch.postValue(it)
                }, { throwable ->
                    Log.d(TAG, "searchContentMore: ${throwable.toString()}")
                    responseState.postValue(FAIL)
                })
        )
    }
}