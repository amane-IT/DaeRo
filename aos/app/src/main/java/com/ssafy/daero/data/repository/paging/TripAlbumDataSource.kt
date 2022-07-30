package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.application.App
import com.ssafy.daero.data.dto.trip.TripAlbumItem
import com.ssafy.daero.data.remote.TripApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TripAlbumDataSource(private val tripApi: TripApi) : RxPagingSource<Int, TripAlbumItem>() {
    override fun getRefreshKey(state: PagingState<Int, TripAlbumItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.trip_seq
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TripAlbumItem>> {
        val page = params.key ?: 1
        return tripApi.getMyAlbum(App.prefs.userSeq, page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = page + 1
                ) as LoadResult<Int, TripAlbumItem>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}