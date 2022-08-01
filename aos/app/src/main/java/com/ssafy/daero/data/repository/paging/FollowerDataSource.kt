package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.data.dto.user.FollowResponseDto
import com.ssafy.daero.data.remote.SnsApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class FollowerDataSource(private val snsApi: SnsApi, private val userSeq: Int) : RxPagingSource<Int, FollowResponseDto>() {
    override fun getRefreshKey(state: PagingState<Int, FollowResponseDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.user_seq
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FollowResponseDto>> {
        val page = params.key ?: 1
        return snsApi.follower(userSeq, page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == it.total_page) null else page + 1
                ) as LoadResult<Int, FollowResponseDto>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}