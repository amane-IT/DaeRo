package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.*
import com.ssafy.daero.data.dto.trip.TripAlbumItem
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ArticleViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()
    val likeState = MutableLiveData<Int>()
    lateinit var articleData: ArticleResponseDto


//    private val _likeUsers = MutableLiveData<PagingData<LikeItem>>()
//    val likeUsers : LiveData<PagingData<LikeItem>>
//        get() = _likeUsers
//
//    val likeUsersState = MutableLiveData<Int>()

//    private val _comment = MutableLiveData< PagingData<CommentItem>>()
//    val comment : LiveData<PagingData<CommentItem>>
//        get() = _comment
//
//    private val _reComment = MutableLiveData< PagingData<ReCommentItem>>()
//    val reComment : LiveData<PagingData<ReCommentItem>>
//        get() = _reComment

    fun article(articleSeq: Int) {

        addDisposable(
            snsRepository.article(articleSeq)
                .subscribe({ response ->
                    articleData = ArticleResponseDto(response.body()!!.user_seq,
                        response.body()!!.nickname, response.body()!!.profile_url,
                        response.body()!!.like_yn, response.body()!!.title,
                        response.body()!!.trip_comment, response.body()!!.trip_expenses,
                        response.body()!!.rating, response.body()!!.likes,
                        response.body()!!.comments, response.body()!!.tags,
                        response.body()!!.records
                        )
                    responseState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    responseState.postValue(FAIL)
                })
        )
    }

    fun likeAdd(userSeq: Int, articleSeq: Int) {

        addDisposable(
            snsRepository.likeAdd(userSeq, articleSeq)
                .subscribe({
                    likeState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    likeState.postValue(FAIL)
                    })
        )
    }

    fun likeDelete(userSeq: Int, articleSeq: Int) {

        addDisposable(
            snsRepository.likeDelete(userSeq, articleSeq)
                .subscribe({
                    likeState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    likeState.postValue(FAIL)
                })
        )
    }

//    fun getLikeUsers(articleSeq: Int) {
//        addDisposable(
//            snsRepository.getLikeUsers(articleSeq)
//                .cachedIn(viewModelScope)
//                .subscribe({ it ->
//                    _likeUsers.postValue(it)
//                }, { throwable ->
//                    Log.d("ArticleVM_DaeRo", throwable.toString())
//                    likeUsersState.postValue(FAIL)
//                })
//        )
//    }

//    fun commentSelect(articleSeq: Int) {
//
//        addDisposable(
//            snsRepository.commentSelect(articleSeq).cachedIn(viewModelScope)
//                .subscribe({
//                    _comment.postValue(it)
//                }, { throwable ->
//                    Log.d("commentSelectVM_DaeRo", throwable.toString())
//                })
//        )
//    }


//    fun commentAdd(
//        articleSeq: Int,
//        commentAddRequestDto: CommentAddRequestDto
//    ) {
//        addDisposable(
//            snsRepository.commentAdd(articleSeq, commentAddRequestDto)
//                .subscribe({
//                    responseState.postValue(SUCCESS)
//                }, { throwable ->
//                    Log.d("commentSelectVM_DaeRo", throwable.toString())
//                    responseState.postValue(FAIL)
//                })
//        )
//    }
//
//    fun commentUpdate(
//        replySeq: Int,
//        commentAddRequestDto: CommentAddRequestDto
//    ) {
//
//        addDisposable(
//            snsRepository.commentUpdate(replySeq, commentAddRequestDto)
//                .subscribe({
//                    responseState.postValue(SUCCESS)
//                }, { throwable ->
//                    Log.d("commentSelectVM_DaeRo", throwable.toString())
//                    responseState.postValue(FAIL)
//                })
//        )
//    }
//
//    fun commentDelete(replySeq: Int) {
//
//        addDisposable(
//            snsRepository.commentDelete(replySeq)
//                .subscribe({
//                    responseState.postValue(SUCCESS)
//                }, { throwable ->
//                    Log.d("commentSelectVM_DaeRo", throwable.toString())
//                    responseState.postValue(FAIL)
//                })
//        )
//    }
//
//    fun reCommentAdd(articleSeq: Int, replySeq: Int, commentAddRequestDto: CommentAddRequestDto) {
//
//        addDisposable(
//            snsRepository.reCommentAdd(articleSeq,replySeq, commentAddRequestDto)
//                .subscribe({
//                    responseState.postValue(SUCCESS)
//                }, { throwable ->
//                    Log.d("commentSelectVM_DaeRo", throwable.toString())
//                    responseState.postValue(FAIL)
//                })
//        )
//    }
//
//
//    fun reCommentSelect(articleSeq: Int, replySeq: Int) {
//
//        addDisposable(
//            snsRepository.reCommentSelect(articleSeq, replySeq).cachedIn(viewModelScope)
//                .subscribe({
//                    _reComment.postValue(it)
//                }, { throwable ->
//                    Log.d("reCommentSelectVM_DaeRo", throwable.toString())
//                })
//        )
//    }
}