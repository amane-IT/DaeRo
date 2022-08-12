package com.ssafy.daero.data.repository

import android.content.Context
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.data.dto.service.FAQResponseDto
import com.ssafy.daero.data.dto.service.InquiryRequestDto
import com.ssafy.daero.data.dto.service.InquiryResponseDto
import com.ssafy.daero.data.dto.service.NoticeResponseDto
import com.ssafy.daero.data.remote.ServiceApi
import com.ssafy.daero.utils.retrofit.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Query

class ServiceRepository private constructor(context: Context) {

    // Service API
    private val serviceApi = RetrofitBuilder.retrofit.create(ServiceApi::class.java)

    fun getNotices(): Single<Response<List<NoticeResponseDto>>> {
        return serviceApi.getNotices()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFaqs(): Single<Response<List<FAQResponseDto>>> {
        return serviceApi.getFaqs()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getInquiry(): Single<Response<List<InquiryResponseDto>>> {
        return serviceApi.getInquiry()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertInquiry(userSeq: Int, inquiryRequestDto: InquiryRequestDto): Completable {
        return serviceApi.insertInquiry(userSeq, inquiryRequestDto)
    }

    companion object {
        private var instance: ServiceRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = ServiceRepository(context)
            }
        }

        fun get(): ServiceRepository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }

}