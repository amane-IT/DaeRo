package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.data.dto.search.ArticleMoreItem
import com.ssafy.daero.data.dto.search.UserNameItem
import com.ssafy.daero.data.remote.SnsApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchContentMoreDataSource(private val snsApi: SnsApi, private val searchKeyword: String) : RxPagingSource<Int, ArticleMoreItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleMoreItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.user_seq
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<PagingSource.LoadResult<Int, ArticleMoreItem>> {
        val page = params.key ?: 1
        return snsApi.searchContentMore(searchKeyword, page)
            .subscribeOn(Schedulers.io())
            .map{
                PagingSource.LoadResult.Page(
                    data = it.results,
                    prevKey = if(page == 1) null else page - 1,
                    nextKey = if (page == it.total_page) null else page + 1
                ) as PagingSource.LoadResult<Int, ArticleMoreItem>
            }
            .onErrorReturn {
                PagingSource.LoadResult.Error(it)
            }
    }

}