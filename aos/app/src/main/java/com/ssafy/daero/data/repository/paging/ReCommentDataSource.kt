package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.data.dto.article.ReCommentItem
import com.ssafy.daero.data.remote.SnsApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ReCommentDataSource(private val snsApi: SnsApi, private val articleSeq: Int, private val replySeq: Int) : RxPagingSource<Int, ReCommentItem>() {
    override fun getRefreshKey(state: PagingState<Int, ReCommentItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ReCommentItem>> {
        val page = params.key ?: 1
        return snsApi.reCommentSelect(articleSeq, replySeq,page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = null,
                    nextKey = if (page >= it.total_page) null else page + 1
                ) as LoadResult<Int, ReCommentItem>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}