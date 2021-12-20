package com.gevorg89.graphql

import com.apollographql.apollo3.ApolloClient
import com.sf.CharactersQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        CoroutineScope(Dispatchers.IO).launch {
            val apolloClient = ApolloClient.Builder()
                .serverUrl("https://rickandmortyapi.com/graphql")
                .build()

            val response = apolloClient.query(CharactersQuery()).execute()
            println()
        }
        Thread.sleep(50000)

    }
}