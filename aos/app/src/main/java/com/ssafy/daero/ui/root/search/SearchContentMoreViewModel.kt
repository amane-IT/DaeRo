package com.ssafy.daero.ui.root.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.search.ArticleMoreItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL

class SearchContentMoreViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()

    private val _resultContentSearch = MutableLiveData<PagingData<ArticleMoreItem>>()
    val resultContentSearch: LiveData<PagingData<ArticleMoreItem>>
        get() = _resultContentSearch

    fun searchContentMore(searchKeyWord: String) {
        addDisposable(
            snsRepository.searchContentMore(searchKeyWord).cachedIn(viewModelScope)
                .subscribe({
                    _resultContentSearch.postValue(it)
                }, { throwable ->
                    responseState.postValue(FAIL)
                })
        )
    }
}