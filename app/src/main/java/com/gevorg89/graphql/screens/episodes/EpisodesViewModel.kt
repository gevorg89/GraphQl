package com.gevorg89.graphql.screens.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class EpisodesViewModel : ViewModel() {

    private var filterEpisodeName: String = ""

    private var episodesPaging = newPagingSource()

    fun filterName(name: String) {
        if (filterEpisodeName == name) {
            return
        }
        this.filterEpisodeName = name
        episodesPaging.invalidate()
    }

    private fun newPagingSource(): EpisodesPaging {
        episodesPaging = EpisodesPaging(name = { filterEpisodeName })
        return episodesPaging
    }

    val episodesPagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        newPagingSource()
    }.flow
        .cachedIn(viewModelScope)
}