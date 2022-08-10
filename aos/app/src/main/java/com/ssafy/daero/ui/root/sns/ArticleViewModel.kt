package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.*
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ArticleViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val responseState = MutableLiveData<Int>()
    val likeState = MutableLiveData<Int>()
    val deleteState = MutableLiveData<Int>()
    val exposeState = MutableLiveData<Int>()
    lateinit var articleData: ArticleResponseDto

    fun article(articleSeq: Int) {

        addDisposable(
            snsRepository.article(articleSeq)
                .subscribe({ response ->
                    articleData = ArticleResponseDto(response.body()!!.user_seq,
                        response.body()!!.nickname, response.body()!!.profile_url,
                        response.body()!!.like_yn, response.body()!!.title, response.body()!!.expose,
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

    fun articleDelete(articleSeq: Int) {

        addDisposable(
            snsRepository.articleDelete(articleSeq)
                .subscribe({
                    deleteState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    deleteState.postValue(FAIL)
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

    fun articleClose(articleSeq: Int) {
        addDisposable(
            snsRepository.articleClose(articleSeq)
                .subscribe({
                    exposeState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    exposeState.postValue(FAIL)
                })
        )
    }

    fun articleOpen(articleSeq: Int) {
        addDisposable(
            snsRepository.articleOpen(articleSeq)
                .subscribe({
                    exposeState.postValue(SUCCESS)
                }, { throwable ->
                    Log.d("ArticleVM_DaeRo", throwable.toString())
                    exposeState.postValue(FAIL)
                })
        )
    }
}