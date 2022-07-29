package com.ssafy.daero.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.ssafy.daero.data.dto.article.ArticleResponseDto
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.data.dto.article.CommentResponseDto
import com.ssafy.daero.data.dto.article.ReCommentResponseDto
import com.ssafy.daero.data.remote.SnsApi
import com.ssafy.daero.data.repository.paging.CommentDataSource
import com.ssafy.daero.data.repository.paging.ReCommentDataSource
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response

class SnsRepository private constructor(context: Context) {

    // Sns API
    private val snsApi = RetrofitBuilder.retrofit.create(SnsApi::class.java)

    fun article(articleSeq: Int): Single<Response<ArticleResponseDto>> {
        return snsApi.article(articleSeq)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun commentSelect(articleSeq: Int): Flowable<PagingData<CommentResponseDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {CommentDataSource(snsApi, articleSeq)}
        ).flowable
    }

    fun commentAdd(articleSeq: Int, commentAddRequestDto: CommentAddRequestDto): Completable {
        return snsApi.commentAdd(articleSeq,commentAddRequestDto)
    }

    fun commentUpdate(replySeq: Int, commentAddRequestDto: CommentAddRequestDto): Completable {
        return snsApi.commentUpdate(replySeq,commentAddRequestDto)
    }

    fun commentDelete(replySeq: Int): Completable {
        return snsApi.commentDelete(replySeq)
    }

    fun reCommentAdd(articleSeq: Int, replySeq: Int, commentAddRequestDto: CommentAddRequestDto): Completable {
        return snsApi.reCommentAdd(articleSeq, replySeq, commentAddRequestDto)
    }

    fun reCommentSelect(articleSeq: Int, replySeq: Int): Flowable<PagingData<ReCommentResponseDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { ReCommentDataSource(snsApi, articleSeq, replySeq) }
        ).flowable
    }

    companion object {
        private var instance: SnsRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = SnsRepository(context)
            }
        }

        fun get(): SnsRepository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }

}