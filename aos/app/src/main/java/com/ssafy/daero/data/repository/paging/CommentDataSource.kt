package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.application.App
import com.ssafy.daero.data.dto.article.CommentResponseDto
import com.ssafy.daero.data.remote.SnsApi
import com.ssafy.daero.data.remote.TripApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CommentDataSource(private val snsApi: SnsApi, private val articleSeq: Int) : RxPagingSource<Int, CommentResponseDto>() {
    override fun getRefreshKey(state: PagingState<Int, CommentResponseDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.reply_seq
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, CommentResponseDto>> {
        val page = params.key ?: 1
        return snsApi.commentSelect(articleSeq, page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = page + 1
                ) as LoadResult<Int, CommentResponseDto>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}