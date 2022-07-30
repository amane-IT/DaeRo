package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.ReportRequestDto
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ReportViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val reportState = MutableLiveData<Int>()

    fun reportArticle(articleSeq: Int, report_seq: Int) {
        addDisposable(
            snsRepository.reportArticle(articleSeq, ReportRequestDto(report_seq))
                .subscribe(
                    {
                        reportState.postValue(SUCCESS)
                    },
                    { throwable ->
                        Log.d("ReportVM", throwable.toString())
                        reportState.postValue(FAIL)
                    }
                )
        )
    }

    fun reportComment(replySeq: Int, report_seq: Int) {
        addDisposable(
            snsRepository.reportComment(replySeq, ReportRequestDto(report_seq))
                .subscribe(
                    {
                        reportState.postValue(SUCCESS)
                    },
                    { throwable ->
                        Log.d("ReportVM", throwable.toString())
                        reportState.postValue(FAIL)
                    }
                )
        )
    }
}