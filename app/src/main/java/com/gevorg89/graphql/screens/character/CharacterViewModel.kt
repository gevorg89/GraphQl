package com.gevorg89.graphql.screens.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gevorg89.graphql.AppApolloClient
import com.sf.CharacterQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel : ViewModel() {

    private val _characterStateFlow: MutableStateFlow<CharacterQuery.Character?> =
        MutableStateFlow(null)
    val characterStateFlow: StateFlow<CharacterQuery.Character?> = _characterStateFlow.asStateFlow()

    fun loadCharacter(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val character =
                    AppApolloClient.apolloClient.query(CharacterQuery(characterId = id)).execute()
                _characterStateFlow.value = character.data?.character
            }
        }
    }

    fun reset() {
        _characterStateFlow.value = null
    }
}