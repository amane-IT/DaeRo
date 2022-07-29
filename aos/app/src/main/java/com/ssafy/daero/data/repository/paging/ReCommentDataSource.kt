package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.data.dto.article.ReCommentResponseDto
import com.ssafy.daero.data.remote.SnsApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ReCommentDataSource(private val snsApi: SnsApi, private val articleSeq: Int, private val replySeq: Int) : RxPagingSource<Int, ReCommentResponseDto>() {
    override fun getRefreshKey(state: PagingState<Int, ReCommentResponseDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.reply_seq
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ReCommentResponseDto>> {
        val page = params.key ?: 1
        return snsApi.reCommentSelect(articleSeq, replySeq,page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = page + 1
                ) as LoadResult<Int, ReCommentResponseDto>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}