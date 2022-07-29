package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.ArticleResponseDto
import com.ssafy.daero.data.dto.article.CommentResponseDto
import com.ssafy.daero.data.dto.article.ReCommentResponseDto
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class CommentViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()
    lateinit var commentData: List<CommentResponseDto>
    lateinit var reCommentData: List<ReCommentResponseDto>

    fun commentSelect(articleSeq: Int, page: Int) {

        addDisposable(
            snsRepository.commentSelect(articleSeq, page)
                .subscribe({ response ->
                    commentData = response.body()!!
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun reCommentSelect(articleSeq: Int, replySeq: Int, page: Int) {

        addDisposable(
            snsRepository.reCommentSelect(articleSeq, replySeq, page)
                .subscribe({ response ->
                    reCommentData = response.body()!!
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }
}