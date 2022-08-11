package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.data.dto.article.ArticleHomeItem
import com.ssafy.daero.data.remote.SnsApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ArticleDataSource(private val snsApi: SnsApi) :
    RxPagingSource<Int, ArticleHomeItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleHomeItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.article_seq
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ArticleHomeItem>> {
        val page = params.key ?: 1
        return snsApi.getArticles(page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page >= it.total_page) null else page + 1
                ) as LoadResult<Int, ArticleHomeItem>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}