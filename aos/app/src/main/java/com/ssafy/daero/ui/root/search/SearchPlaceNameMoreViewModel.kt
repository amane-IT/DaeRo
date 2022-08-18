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

class SearchPlaceNameMoreViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()

    private val _resultPlaceNameSearch = MutableLiveData<PagingData<ArticleMoreItem>>()
    val resultPlaceNameSearch: LiveData<PagingData<ArticleMoreItem>>
        get() = _resultPlaceNameSearch

    fun searchPlaceNameMore(searchKeyWord: String) {
        addDisposable(
            snsRepository.searchPlaceMore(searchKeyWord).cachedIn(viewModelScope)
                .subscribe({
                    _resultPlaceNameSearch.postValue(it)
                }, { throwable ->
                    responseState.postValue(FAIL)
                })
        )
    }
}