package com.gevorg89.graphql.screens.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gevorg89.graphql.AppApolloClient
import com.sf.EpisodeQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodeViewModel : ViewModel() {

    private val _episodeStateFlow: MutableStateFlow<EpisodeQuery.Episode?> =
        MutableStateFlow(null)
    val episodeStateFlow: StateFlow<EpisodeQuery.Episode?> = _episodeStateFlow.asStateFlow()

    fun loadEpisode(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val character =
                    AppApolloClient.apolloClient.query(EpisodeQuery(episodeId = id))
                        .execute()
                _episodeStateFlow.value = character.data?.episode
            }
        }
    }

    fun reset() {
        _episodeStateFlow.value = null
    }
}