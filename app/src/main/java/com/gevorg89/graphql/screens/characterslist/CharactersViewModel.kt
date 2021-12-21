package com.gevorg89.graphql.screens.characterslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gevorg89.graphql.screens.characterslist.CharactersPaging

class CharactersViewModel : ViewModel() {

    private var filterCharacterName: String = ""

    private var charactersPaging = newPagingSource()

    fun filterName(name: String) {
        if (filterCharacterName == name) {
            return
        }
        this.filterCharacterName = name
        charactersPaging.invalidate()
    }

    private fun newPagingSource(): CharactersPaging {
        charactersPaging = CharactersPaging(name = { filterCharacterName })
        return charactersPaging
    }

    val charactersPagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        newPagingSource()
    }.flow
        .cachedIn(viewModelScope)
}