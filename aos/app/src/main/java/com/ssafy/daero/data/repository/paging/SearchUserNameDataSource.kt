package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.data.dto.sns.UserNameItem
import com.ssafy.daero.data.remote.SnsApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchUserNameDataSource(private val snsApi: SnsApi, private val searchKeyword: String) : RxPagingSource<Int, UserNameItem>() {
    override fun getRefreshKey(state: PagingState<Int, UserNameItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.user_seq
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, UserNameItem>> {
        val page = params.key ?: 1
        return snsApi.searchUserName(searchKeyword, page)
            .subscribeOn(Schedulers.io())
            .map{
                LoadResult.Page(
                    data = it.results,
                    prevKey = if(page == 1) null else page - 1,
                    nextKey = if (page == it.total_page) null else page + 1
                ) as LoadResult<Int, UserNameItem>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

}