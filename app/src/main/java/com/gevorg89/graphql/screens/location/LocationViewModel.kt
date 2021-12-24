package com.gevorg89.graphql.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gevorg89.graphql.AppApolloClient
import com.sf.LocationQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationViewModel : ViewModel() {

    private val _locationStateFlow: MutableStateFlow<LocationQuery.Location?> =
        MutableStateFlow(null)
    val locationStateFlow: StateFlow<LocationQuery.Location?> = _locationStateFlow.asStateFlow()

    fun loadLocation(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val character =
                    AppApolloClient.apolloClient.query(LocationQuery(locationId = id))
                        .execute()
                _locationStateFlow.value = character.data?.location
            }
        }
    }

    fun reset() {
        _locationStateFlow.value = null
    }
}