package com.gevorg89.graphql

import com.apollographql.apollo3.ApolloClient
import com.sf.CharactersQuery


object ApolloClientTest {

    suspend fun characters(name: String): CharactersQuery.Data? {
        // Create a client
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .build()

        val response = apolloClient.query(CharactersQuery(name = name)).execute()
        return response.data
    }

}