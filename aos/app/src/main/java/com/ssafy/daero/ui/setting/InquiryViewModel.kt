package com.ssafy.daero.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.service.FAQResponseDto
import com.ssafy.daero.data.dto.service.InquiryRequestDto
import com.ssafy.daero.data.dto.service.InquiryResponseDto
import com.ssafy.daero.data.repository.ServiceRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class InquiryViewModel : BaseViewModel() {
    private val serviceRepository = ServiceRepository.get()

    var inquiryList = listOf<InquiryResponseDto>()

    val inquiryState = MutableLiveData<Int>()

    fun getInquiry() {
        addDisposable(
            serviceRepository.getInquiry()
                .subscribe({ response ->
                    inquiryList = response.body()!!
                    inquiryState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("FaqVM", throwable.toString())
                    inquiryState.postValue(FAIL)
                })
        )
    }

    fun insertInquiry(userSeq: Int, inquiryRequestDto: InquiryRequestDto) {
        addDisposable(
            serviceRepository.insertInquiry(userSeq, inquiryRequestDto)
                .subscribe({
                    inquiryState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                    inquiryState.postValue(FAIL)
                })
        )
    }
}