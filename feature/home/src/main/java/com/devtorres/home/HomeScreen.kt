package com.devtorres.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onNavigateToAnimalDetail: (String) -> Unit
) {
    Column {
        Text(
            text = "Home"
        )

        Button(
            onClick = { onNavigateToAnimalDetail("1") }
        ) {
            Text("animal")
        }
    }
}