package com.gevorg89.graphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gevorg89.graphql.screens.character.CharacterScreen
import com.gevorg89.graphql.screens.character.CharacterViewModel
import com.gevorg89.graphql.screens.characterslist.CharactersScreen
import com.gevorg89.graphql.screens.characterslist.CharactersViewModel
import com.gevorg89.graphql.ui.theme.GraphQlTheme

class MainActivity : ComponentActivity() {

    private val charactersViewModel: CharactersViewModel by viewModels()
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphQlTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "characters") {
                        composable("characters") {
                            CharactersScreen(
                                charactersPaging = charactersViewModel.charactersPagingFlow,
                                invalidateSearch = {
                                    charactersViewModel.filterName(it)
                                },
                                showCharacter = {
                                    navController.navigate("character/${it.id}")
                                }
                            )
                        }
                        composable("character/{userId}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("userId")
                            val character by characterViewModel.characterStateFlow.collectAsState()
                            CharacterScreen(character = character)
                            DisposableEffect(key1 = id) {
                                characterViewModel.loadCharacter(id.orEmpty())
                                onDispose {
                                    characterViewModel.reset()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
