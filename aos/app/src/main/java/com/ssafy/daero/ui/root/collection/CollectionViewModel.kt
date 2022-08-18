package com.ssafy.daero.ui.root.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.collection.CollectionItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL

class CollectionViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()

    private val _collections = MutableLiveData<PagingData<CollectionItem>>()
    val collections: LiveData<PagingData<CollectionItem>>
        get() = _collections

    fun getCollections() {
        addDisposable(
            snsRepository.getCollection().cachedIn(viewModelScope)
                .subscribe({
                    _collections.postValue(it)
                }, { throwable ->
                    responseState.postValue(FAIL)
                })
        )
    }
}