package com.gevorg89.graphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.gevorg89.graphql.screens.CharactersScreen
import com.gevorg89.graphql.screens.CharactersViewModel
import com.gevorg89.graphql.ui.theme.GraphQlTheme

class MainActivity : ComponentActivity() {

    private val charactersViewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphQlTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CharactersScreen(
                        charactersPaging = charactersViewModel.charactersPagingFlow,
                        invalidateSearch = {
                            charactersViewModel.filterName(it)
                        }
                    )
                }
            }
        }
    }
}
