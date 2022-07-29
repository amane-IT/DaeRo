package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.ArticleResponseDto
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.data.dto.article.CommentResponseDto
import com.ssafy.daero.data.dto.article.ReCommentResponseDto
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import retrofit2.http.Body
import retrofit2.http.Path

class CommentViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()
    lateinit var commentData: List<CommentResponseDto>
    lateinit var reCommentData: List<ReCommentResponseDto>

    private val _comment = MutableLiveData< PagingData<CommentResponseDto>>()
    val comment : LiveData<PagingData<CommentResponseDto>>
        get() = _comment

    private val _reComment = MutableLiveData< PagingData<ReCommentResponseDto>>()
    val reComment : LiveData<PagingData<ReCommentResponseDto>>
        get() = _reComment

    fun commentSelect(articleSeq: Int) {

        addDisposable(
            snsRepository.commentSelect(articleSeq).cachedIn(viewModelScope)
                .subscribe({
                    _comment.postValue(it)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                })
        )
    }

    fun commentAdd(
        articleSeq: Int,
        commentAddRequestDto: CommentAddRequestDto
    ) {
        addDisposable(
            snsRepository.commentAdd(articleSeq, commentAddRequestDto)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun commentUpdate(
        replySeq: Int,
        commentAddRequestDto: CommentAddRequestDto
    ) {

        addDisposable(
            snsRepository.commentUpdate(replySeq, commentAddRequestDto)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun commentDelete(replySeq: Int) {

        addDisposable(
            snsRepository.commentDelete(replySeq)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun reCommentAdd(articleSeq: Int, replySeq: Int, commentAddRequestDto: CommentAddRequestDto) {

        addDisposable(
            snsRepository.reCommentAdd(articleSeq,replySeq, commentAddRequestDto)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("commentSelectVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }


    fun reCommentSelect(articleSeq: Int, replySeq: Int) {

        addDisposable(
            snsRepository.reCommentSelect(articleSeq, replySeq).cachedIn(viewModelScope)
                .subscribe({
                    _reComment.postValue(it)
                }, { throwable ->
                    Log.d("reCommentSelectVM_DaeRo", throwable.toString())
                })
        )
    }
}