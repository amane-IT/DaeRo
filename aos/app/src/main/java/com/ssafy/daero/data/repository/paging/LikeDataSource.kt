package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.data.dto.article.LikeItem
import com.ssafy.daero.data.remote.SnsApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LikeDataSource(private val snsApi: SnsApi, private val articleSeq: Int) :
    RxPagingSource<Int, LikeItem>() {
    override fun getRefreshKey(state: PagingState<Int, LikeItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, LikeItem>> {
        val page = params.key ?: 1
        return snsApi.getLikeUsers(articleSeq, page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = null,
                    nextKey = if (page >= it.total_page) null else page + 1
                ) as LoadResult<Int, LikeItem>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}