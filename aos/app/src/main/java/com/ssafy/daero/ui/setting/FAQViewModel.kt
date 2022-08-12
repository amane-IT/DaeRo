package com.ssafy.daero.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.service.FAQResponseDto
import com.ssafy.daero.data.repository.ServiceRepository
import com.ssafy.daero.utils.constant.FAIL

class FAQViewModel : BaseViewModel() {
    private val serviceRepository = ServiceRepository.get()

    private val _faqs = MutableLiveData<List<FAQResponseDto>>()
    val faqs: LiveData<List<FAQResponseDto>>
        get() = _faqs

    val faqState = MutableLiveData<Int>()

    fun getFaqs() {
        addDisposable(
            serviceRepository.getFaqs()
                .subscribe({ response ->
                    _faqs.postValue(response.body()!!)
                }, { throwable ->
                    faqState.postValue(FAIL)
                })
        )
    }
}