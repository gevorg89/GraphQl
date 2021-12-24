package com.gevorg89.graphql.screens.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.sf.LocationQuery

@Composable
fun LocationScreen(location: LocationQuery.Location?) {
    location?.let {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            item {
                Column(Modifier.fillMaxWidth()) {
                    Text(text = "Location name:", fontSize = 18.sp)
                    Text(text = location.name ?: "", fontSize = 16.sp)
                }
            }
            items(location.residents.filterNotNull()) { resident ->
                Row {
                    Image(
                        painter = rememberImagePainter(data = resident.image),
                        contentDescription = null,
                        modifier = Modifier.size(160.dp)
                    )
                    EpisodesList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        episodes = resident.episode.filterNotNull()
                    )
                }
                Divider()
            }
        }
    }
}

@Composable
fun EpisodesList(modifier: Modifier, episodes: List<LocationQuery.Episode>) {
    Column(modifier = modifier) {
        Text(text = "Episodes:")
        episodes.forEachIndexed { index, episode ->
            Text(text = episode.name ?: "")
            if (index < episodes.size-1) {
                Divider()
            }
        }
    }
}
