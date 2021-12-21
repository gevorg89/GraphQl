package com.gevorg89.graphql.screens

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gevorg89.graphql.AppApolloClient
import com.sf.CharactersQuery

class CharactersPaging(private val name: () -> String) :
    PagingSource<Int, CharactersQuery.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersQuery.Result> {
        val page = params.key ?: 1
        val response =
            AppApolloClient.apolloClient.query(CharactersQuery(page = page, name = name())).execute()

        val data = response.data?.characters?.results?.filterNotNull().orEmpty()
        val nextKey = response.data?.characters?.info?.next
        val prevKey = response.data?.characters?.info?.prev
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

    override fun getRefreshKey(state: PagingState<Int, CharactersQuery.Result>): Int? {
        return null
    }

}