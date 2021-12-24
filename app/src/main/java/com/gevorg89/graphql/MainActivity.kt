package com.gevorg89.graphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gevorg89.graphql.screens.character.CharacterScreen
import com.gevorg89.graphql.screens.character.CharacterViewModel
import com.gevorg89.graphql.screens.characterslist.CharactersScreen
import com.gevorg89.graphql.screens.characterslist.CharactersViewModel
import com.gevorg89.graphql.screens.episode.EpisodeScreen
import com.gevorg89.graphql.screens.episode.EpisodeViewModel
import com.gevorg89.graphql.screens.episodes.EpisodesScreen
import com.gevorg89.graphql.screens.episodes.EpisodesViewModel
import com.gevorg89.graphql.screens.location.LocationScreen
import com.gevorg89.graphql.screens.location.LocationViewModel
import com.gevorg89.graphql.screens.locations.LocationsScreen
import com.gevorg89.graphql.screens.locations.LocationsViewModel
import com.gevorg89.graphql.ui.theme.GraphQlTheme

class MainActivity : ComponentActivity() {

    private val charactersViewModel: CharactersViewModel by viewModels()
    private val characterViewModel: CharacterViewModel by viewModels()

    private val locationsViewModel: LocationsViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()

    private val episodesViewModel: EpisodesViewModel by viewModels()
    private val episodeViewModel: EpisodeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = listOf(
            Screen.CharactersScreen,
            Screen.EpisodesScreen,
            Screen.LocationsScreen,
        )
        setContent {
            GraphQlTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigation() {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                items.forEach { screen ->
                                    BottomNavigationItem(
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_baseline_people_24),
                                                contentDescription = null
                                            )
                                        },
                                        label = { Text(screen.text) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = Screen.CharactersScreen.route
                        ) {
                            composable(Screen.CharactersScreen.route) {
                                CharactersScreen(
                                    charactersPaging = charactersViewModel.charactersPagingFlow,
                                    invalidateSearch = {
                                        charactersViewModel.filterName(it)
                                    },
                                    showCharacter = {
                                        navController.navigate(
                                            Screen.CharacterScreen.route(
                                                it.id ?: ""
                                            )
                                        )
                                    }
                                )
                            }
                            composable(Screen.CharacterScreen.Character.route) { backStackEntry ->
                                val id =
                                    backStackEntry.arguments?.getString(Screen.CharacterScreen.UserIdParam)
                                val character by characterViewModel.characterStateFlow.collectAsState()
                                CharacterScreen(character = character)
                                DisposableEffect(key1 = id) {
                                    characterViewModel.loadCharacter(id.orEmpty())
                                    onDispose {
                                        characterViewModel.reset()
                                    }
                                }
                            }
                            composable(Screen.LocationsScreen.route) {
                                LocationsScreen(
                                    locationsPaging = locationsViewModel.locationsPagingFlow,
                                    invalidateSearch = {
                                        locationsViewModel.filterName(it)
                                    },
                                    showLocation = {
                                        navController.navigate(
                                            Screen.LocationScreen.route(
                                                it.id ?: ""
                                            )
                                        )
                                    }
                                )
                            }
                            composable(Screen.LocationScreen.Location.route) { backStackEntry ->
                                val id =
                                    backStackEntry.arguments?.getString(Screen.LocationScreen.LocationIdParam)
                                        ?: ""
                                val location by locationViewModel.locationStateFlow.collectAsState()
                                LocationScreen(location = location)
                                DisposableEffect(key1 = id) {
                                    locationViewModel.loadLocation(id)
                                    onDispose {
                                        locationViewModel.reset()
                                    }
                                }
                            }

                            composable(Screen.EpisodesScreen.route) {
                                EpisodesScreen(
                                    episodesPaging = episodesViewModel.episodesPagingFlow,
                                    invalidateSearch = {
                                        episodesViewModel.filterName(it)
                                    },
                                    showEpisode = {
                                        navController.navigate(
                                            Screen.EpisodeScreen.route(
                                                it.id ?: ""
                                            )
                                        )
                                    }
                                )
                            }

                            composable(Screen.EpisodeScreen.Episode.route) { backStackEntry ->
                                val id =
                                    backStackEntry.arguments?.getString(Screen.EpisodeScreen.EpisodeIdParam)
                                        ?: ""
                                val episode by episodeViewModel.episodeStateFlow.collectAsState()
                                EpisodeScreen(episode = episode)
                                DisposableEffect(key1 = id) {
                                    episodeViewModel.loadEpisode(id)
                                    onDispose {
                                        episodeViewModel.reset()
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
