package com.gevorg89.graphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.gevorg89.graphql.screens.CharactersScreen
import com.gevorg89.graphql.ui.theme.GraphQlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphQlTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CharactersScreen()
                }
            }
        }
    }
}
