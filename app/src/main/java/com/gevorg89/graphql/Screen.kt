package com.gevorg89.graphql

sealed class Screen(val route: String, val text: String) {
    object CharactersScreen : Screen("characters", "Characters")

    //object Character : Screen("character/{userId}", "Character")
    object CharacterScreen {
        const val UserIdParam = "userId"

        object Character : Screen("character/{$UserIdParam}", "Character")

        fun route(userId: String): String {
            return "character/$userId"
        }
    }

    object LocationsScreen : Screen("locations", "Locations")
    object LocationScreen {
        const val LocationIdParam = "locationId"

        object Location : Screen("location/{$LocationIdParam}", "Location")

        fun route(id: String): String {
            return "location/$id"
        }
    }

    object EpisodesScreen : Screen("episodes", "Episodes")
    object EpisodeScreen {
        const val EpisodeIdParam = "episodeId"

        object Episode : Screen("episode/{$EpisodeIdParam}", "Episode")

        fun route(id: String): String {
            return "episode/$id"
        }
    }
}