package com.gevorg89.graphql.screens.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gevorg89.graphql.AppApolloClient
import com.sf.EpisodesQuery

class EpisodesPaging(private val name: () -> String) :
    PagingSource<Int, EpisodesQuery.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesQuery.Result> {
        val page = params.key ?: 1
        val response =
            AppApolloClient.apolloClient.query(EpisodesQuery(page = page, name = name())).execute()

        val data = response.data?.episodes?.results?.filterNotNull().orEmpty()
        val nextKey = response.data?.episodes?.info?.next
        val prevKey = response.data?.episodes?.info?.prev
        return try {
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodesQuery.Result>): Int? {
        return null
    }

}