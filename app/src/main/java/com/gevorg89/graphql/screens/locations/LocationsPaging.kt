package com.gevorg89.graphql.screens.locations

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gevorg89.graphql.AppApolloClient
import com.sf.LocationsQuery

class LocationsPaging(private val name: () -> String) :
    PagingSource<Int, LocationsQuery.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationsQuery.Result> {
        val page = params.key ?: 1
        val response =
            AppApolloClient.apolloClient.query(LocationsQuery(page = page, name = name())).execute()

        val data = response.data?.locations?.results?.filterNotNull().orEmpty()
        val nextKey = response.data?.locations?.info?.next
        val prevKey = response.data?.locations?.info?.prev
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

    override fun getRefreshKey(state: PagingState<Int, LocationsQuery.Result>): Int? {
        return null
    }

}