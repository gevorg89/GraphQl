package com.gevorg89.graphql.screens.episode

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.sf.EpisodeQuery

@Composable
fun EpisodeScreen(episode: EpisodeQuery.Episode?) {
    episode?.let {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            item {
                Column(Modifier.fillMaxWidth()) {
                    Text(text = "Episode name:", fontSize = 18.sp)
                    Text(text = episode.name ?: "", fontSize = 16.sp)
                }
            }
            items(episode.characters.filterNotNull()) { character ->
                Row {
                    Image(
                        painter = rememberImagePainter(data = character.image),
                        contentDescription = null,
                        modifier = Modifier.size(160.dp)
                    )
                    Text(text = character.name ?: "")
                }
                Divider()
            }
        }
    }
}
