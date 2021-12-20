package com.gevorg89.graphql.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.gevorg89.graphql.ApolloClientTest
import com.sf.CharactersQuery
import kotlinx.coroutines.*

@Composable
fun CharactersScreen() {
    var item by remember {
        mutableStateOf<CharactersQuery.Data?>(null)
    }
    var text by remember { mutableStateOf("") }
    val searchScope = remember { CoroutineScope(Dispatchers.IO) }
    Column {
        Search(text) {
            text = it
        }
        item?.characters?.results?.let {
            Characters(it)
        }
    }

    LaunchedEffect(key1 = text, block = {
        searchScope.coroutineContext.cancelChildren()
        searchScope.launch {
            delay(400)
            item = ApolloClientTest.characters(text)
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
fun Characters(results: List<CharactersQuery.Result?>) {
    LazyColumn {
        items(results) { character ->
            character?.let {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberImagePainter(data = character.image),
                        contentDescription = null,
                        modifier = Modifier.size(160.dp)
                    )
                    Text(text = "Character name is:${character.name}")
                }
            }
        }
    }
}