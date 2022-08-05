package com.ssafy.daero.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.service.FAQResponseDto
import com.ssafy.daero.data.dto.service.InquiryResponseDto
import com.ssafy.daero.data.repository.ServiceRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class InquiryViewModel : BaseViewModel() {
    private val serviceRepository = ServiceRepository.get()

    private val _inquiry = MutableLiveData<List<InquiryResponseDto>>()
    val inquiry: LiveData<List<InquiryResponseDto>>
        get() = _inquiry

    val inquiryState = MutableLiveData<Int>()

    fun getInquiry() {
        addDisposable(
            serviceRepository.getInquiry()
                .subscribe({ response ->
                    _inquiry.postValue(response.body()!!)
                }, { throwable ->
                    Log.d("FaqVM", throwable.toString())
                    inquiryState.postValue(FAIL)
                })
        )
    }

    fun insertInquiry(userSeq: Int) {
        addDisposable(
            serviceRepository.insertInquiry(userSeq)
                .subscribe({
                    inquiryState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                    inquiryState.postValue(FAIL)
                })
        )
    }
}