package com.gevorg89.graphql.screens.episodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.sf.EpisodesQuery
import com.sf.LocationsQuery
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

@Composable
fun EpisodesScreen(
    episodesPaging: Flow<PagingData<EpisodesQuery.Result>>,
    invalidateSearch: (String) -> Unit,
    showEpisode: (EpisodesQuery.Result) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val pagingItems = episodesPaging.collectAsLazyPagingItems()
    val searchScope = remember { CoroutineScope(Dispatchers.IO) }
    Column {
        Search(text) {
            text = it
        }
        Episode(pagingItems = pagingItems, showEpisode = showEpisode)
    }

    LaunchedEffect(key1 = text, block = {
        searchScope.coroutineContext.cancelChildren()
        searchScope.launch {
            delay(400)
            invalidateSearch(text)
        }
    })
}

@Composable
fun Search(text: String, onValueChange: (String) -> Unit) {
    TextField(modifier = Modifier.fillMaxWidth(), value = text, onValueChange = {
        onValueChange(it)
    })
}

@Composable
fun Episode(
    pagingItems: LazyPagingItems<EpisodesQuery.Result>,
    showEpisode: (EpisodesQuery.Result) -> Unit
) {
    LazyColumn {
        items(pagingItems) { episode ->
            episode?.let {
                Box() {
                    Column(modifier = Modifier

                        .fillMaxWidth()
                        .clickable { showEpisode(episode) }
                        .padding(16.dp)) {
                        Text(
                            text = "Episode name is:",
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight(700)
                        )
                        Text(text = "${episode.name}", fontSize = 16.sp)

                    }
                    Divider()
                }

            }
        }
    }
}