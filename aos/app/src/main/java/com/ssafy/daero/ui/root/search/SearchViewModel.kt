package com.ssafy.daero.ui.root.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.ArticleResponseDto
import com.ssafy.daero.data.dto.search.ArticleItem
import com.ssafy.daero.data.dto.search.SearchArticleResponseDto
import com.ssafy.daero.data.dto.search.UserNameItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import retrofit2.Response

class SearchViewModel : BaseViewModel() {
    private val TAG = "SearchVM_DaeRo"
    private val snsRepository = SnsRepository.get()

    val responseState_userName = MutableLiveData<Int>()
    val responseState_articles = MutableLiveData<Int>()
    val state_article_content = MutableLiveData<Int>()
    val state_article_placeName = MutableLiveData<Int>()

    private val _resultUserSearch = MutableLiveData<PagingData<UserNameItem>>()
    val resultUserSearch: LiveData<PagingData<UserNameItem>>
        get() = _resultUserSearch

    private val _resultArticleSearch = MutableLiveData<SearchArticleResponseDto>()
    val resultArticleSearch: LiveData<SearchArticleResponseDto>
        get() = _resultArticleSearch

    fun searchUserName(searchKeyWord: String){
        addDisposable(
            snsRepository.searchUserName(searchKeyWord).cachedIn(viewModelScope)
                .subscribe({
                    Log.d(TAG, "searchUserName: $it")
                    _resultUserSearch.postValue(it)
                }, { throwable ->
                    Log.d(TAG, "searchUserName: ${throwable.toString()}")
                    responseState_userName.postValue(FAIL)
                })
        )
    }

    fun searchArticle(searchKeyWord: String){
        addDisposable(
            snsRepository.searchArticle(searchKeyWord)
                .subscribe({response ->
                    _resultArticleSearch.postValue(response.body())
                    Log.d(TAG, "content is: ${response.body()!!.content.isEmpty()}")
                    when(response.body()!!.content.isEmpty()){
                        false -> state_article_content.postValue(SUCCESS)
                        true -> state_article_content.postValue(FAIL)
                    }

                    when(response.body()!!.place.isEmpty()){
                        false -> state_article_placeName.postValue(SUCCESS)
                        true -> state_article_placeName.postValue(FAIL)
                    }

                }, {throwable ->
                    Log.d(TAG, "searchArticle: ${throwable.toString()}")
                    responseState_articles.postValue(FAIL)
                })
        )
    }
}