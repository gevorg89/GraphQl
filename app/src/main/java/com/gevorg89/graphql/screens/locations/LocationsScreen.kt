package com.gevorg89.graphql.screens.locations

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
import com.sf.LocationsQuery
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

@Composable
fun LocationsScreen(
    locationsPaging: Flow<PagingData<LocationsQuery.Result>>,
    invalidateSearch: (String) -> Unit,
    showLocation: (LocationsQuery.Result) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val pagingItems = locationsPaging.collectAsLazyPagingItems()
    val searchScope = remember { CoroutineScope(Dispatchers.IO) }
    Column {
        Search(text) {
            text = it
        }
        Locations(pagingItems = pagingItems, showLocation = showLocation)
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
fun Locations(
    pagingItems: LazyPagingItems<LocationsQuery.Result>,
    showLocation: (LocationsQuery.Result) -> Unit
) {
    LazyColumn {
        items(pagingItems) { location ->
            location?.let {
                Box() {
                    Column(modifier = Modifier

                        .fillMaxWidth()
                        .clickable { showLocation(location) }
                        .padding(16.dp)) {
                        /*Image(
                            painter = rememberImagePainter(data = location.),
                            contentDescription = null,
                            modifier = Modifier.size(160.dp)
                        )*/
                        Text(
                            text = "Location name is:",
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight(700)
                        )
                        Text(text = "${location.name}", fontSize = 16.sp)

                    }
                    Divider()
                }

            }
        }
    }
}