package com.ssafy.daero.ui.root.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.search.SearchArticleResponseDto
import com.ssafy.daero.data.dto.search.UserNameItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SearchViewModel : BaseViewModel() {
    private val TAG = "SearchVM_DaeRo"
    private val snsRepository = SnsRepository.get()

    val responseState_userName = MutableLiveData<Int>()
    val responseState_articles = MutableLiveData<Int>()

    private val _resultUserSearch = MutableLiveData<PagingData<UserNameItem>>()
    val resultUserSearch: LiveData<PagingData<UserNameItem>>
        get() = _resultUserSearch

    private val _resultArticleSearch = MutableLiveData<SearchArticleResponseDto>()
    val resultArticleSearch: LiveData<SearchArticleResponseDto>
        get() = _resultArticleSearch

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    fun searchUserName(searchKeyWord: String) {
        _showProgress.postValue(true)
        addDisposable(
            snsRepository.searchUserName(searchKeyWord).cachedIn(viewModelScope)
                .subscribe({
                    Log.d(TAG, "searchUserName: $it")
                    _resultUserSearch.postValue(it)
                    responseState_userName.postValue(SUCCESS)
                    _showProgress.postValue(false)
                }, { throwable ->
                    Log.d(TAG, "searchUserName: $throwable")
                    responseState_userName.postValue(FAIL)
                    _showProgress.postValue(false)
                })
        )
    }

    fun searchArticle(searchKeyWord: String) {
        _showProgress.postValue(true)
        addDisposable(
            snsRepository.searchArticle(searchKeyWord)
                .subscribe({ response ->
                    _showProgress.postValue(false)

                    // 검색 결과 없는 경우
                    if(response.body()!!.content.isEmpty() && response.body()!!.place.isEmpty()) {
                        responseState_articles.postValue(FAIL)
                        return@subscribe
                    }

                    responseState_articles.postValue(SUCCESS)
                    _resultArticleSearch.postValue(response.body())
                }, { throwable ->
                    Log.d(TAG, "searchArticle: $throwable")
                    responseState_articles.postValue(FAIL)
                    _showProgress.postValue(false)
                })
        )
    }
}