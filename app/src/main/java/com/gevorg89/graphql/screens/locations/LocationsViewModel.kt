package com.gevorg89.graphql.screens.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class LocationsViewModel : ViewModel() {

    private var filterLocationName: String = ""

    private var charactersPaging = newPagingSource()

    fun filterName(name: String) {
        if (filterLocationName == name) {
            return
        }
        this.filterLocationName = name
        charactersPaging.invalidate()
    }

    private fun newPagingSource(): LocationsPaging {
        charactersPaging = LocationsPaging(name = { filterLocationName })
        return charactersPaging
    }

    val locationsPagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        newPagingSource()
    }.flow
        .cachedIn(viewModelScope)
}