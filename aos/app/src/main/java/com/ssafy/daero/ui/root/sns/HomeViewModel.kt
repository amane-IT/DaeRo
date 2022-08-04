package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.ArticleHomeItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL

class HomeViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    private val _articles = MutableLiveData<PagingData<ArticleHomeItem>>()
    val articles: LiveData<PagingData<ArticleHomeItem>>
        get() = _articles

    val articleState = MutableLiveData<Int>()

    fun getArticles() {
        addDisposable(
            snsRepository.getArticles().cachedIn(viewModelScope)
                .subscribe({
                    _articles.postValue(it)
                }, { throwable ->
                    Log.d("HomeVM_DaeRo", throwable.toString())
                    articleState.postValue(FAIL)
                }
                )
        )
    }
}