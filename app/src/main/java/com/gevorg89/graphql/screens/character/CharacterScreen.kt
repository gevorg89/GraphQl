package com.gevorg89.graphql.screens.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.sf.CharacterQuery

@Composable
fun CharacterScreen(character: CharacterQuery.Character?) {
    character?.let {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                CharacterImage(character.image.orEmpty())
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        CharacterName(character.name.orEmpty())
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        CharacterStatus(character.status.orEmpty())
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    CharacterCreated(character.created.orEmpty())
                }
            }
            items(character.episode.filterNotNull()) { episode ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    EpisodeItem(episode)
                }
            }
        }
    }
}

@Composable
private fun EpisodeItem(episode: CharacterQuery.Episode) {
    Text(
        text = "Episode: ${episode.episode}",
        fontSize = 16.sp
    )
    Text(
        text = "Name: ${episode.name}",
        fontSize = 16.sp
    )
}

@Composable
private fun CharacterCreated(created: String) {
    Text(
        text = "Created:",
        fontSize = 20.sp
    )
    Text(
        text = created,
        fontSize = 16.sp
    )
}

@Composable
private fun CharacterName(name: String) {
    Text(
        text = "Name:",
        fontSize = 20.sp
    )
    Text(
        text = name,
        fontSize = 16.sp
    )
}

@Composable
private fun CharacterStatus(status: String) {
    Text(
        text = "Status:",
        fontSize = 20.sp
    )
    Text(
        text = status,
        fontSize = 16.sp
    )
}

@Composable
private fun CharacterImage(image: String) {
    Image(
        painter = rememberImagePainter(data = image),
        contentDescription = null,
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    )
}