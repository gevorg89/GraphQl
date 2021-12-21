package com.gevorg89.graphql

import com.sf.CharactersQuery


object ApolloClientTest {

    suspend fun characters(name: String): CharactersQuery.Data? {
        val response =
            AppApolloClient.apolloClient.query(CharactersQuery(name = name, page = 0)).execute()
        return response.data
    }

}