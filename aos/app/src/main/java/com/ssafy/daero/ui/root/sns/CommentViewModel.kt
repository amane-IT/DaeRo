package com.ssafy.daero.ui.root.sns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.data.dto.article.CommentItem
import com.ssafy.daero.data.dto.article.ReCommentItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class CommentViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    private val _comment = MutableLiveData<PagingData<CommentItem>>()
    val comment: LiveData<PagingData<CommentItem>>
        get() = _comment

    private val _reComment = MutableLiveData<PagingData<ReCommentItem>>()
    val reComment: LiveData<PagingData<ReCommentItem>>
        get() = _reComment

    val responseState = MutableLiveData<Int>()

    fun commentSelect(articleSeq: Int) {

        addDisposable(
            snsRepository.commentSelect(articleSeq).cachedIn(viewModelScope)
                .subscribe({
                    _comment.postValue(it)
                }, { throwable ->
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
                    responseState.postValue(FAIL)
                })
        )
    }

    fun reCommentAdd(articleSeq: Int, replySeq: Int, commentAddRequestDto: CommentAddRequestDto) {

        addDisposable(
            snsRepository.reCommentAdd(articleSeq, replySeq, commentAddRequestDto)
                .subscribe({
                    responseState.postValue(SUCCESS)
                }, { throwable ->
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
                })
        )
    }
}