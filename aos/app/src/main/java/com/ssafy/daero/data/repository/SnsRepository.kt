package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.data.dto.article.ArticleResponseDto
import com.ssafy.daero.data.dto.article.CommentResponseDto
import com.ssafy.daero.data.dto.article.ReCommentResponseDto
import com.ssafy.daero.data.remote.SnsApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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

    fun commentSelect(articleSeq: Int, page: Int): Single<Response<List<CommentResponseDto>>> {
        return snsApi.commentSelect(articleSeq,page)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun reCommentSelect(articleSeq: Int, replySeq: Int, page: Int): Single<Response<List<ReCommentResponseDto>>> {
        return snsApi.reCommentSelect(articleSeq,replySeq,page)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
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