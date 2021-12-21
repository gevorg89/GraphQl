package com.gevorg89.graphql

import com.apollographql.apollo3.ApolloClient

object AppApolloClient {

    // Create a client
    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()

}