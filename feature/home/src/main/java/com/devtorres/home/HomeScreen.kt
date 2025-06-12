package com.devtorres.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    onNavigateToAnimalDetail: (String) -> Unit,
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val animalList by homeViewModel.animalList.collectAsStateWithLifecycle()


    Column {
        CatApiTopAppBar()

        if(animalList.isEmpty()) {
            Text(text = "No hay animales")
        } else {
            Text(text = "Hay ${animalList.size} animales")
        }
    }

    if(uiState is HomeUiState.Loading) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatApiTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "CatDex",
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}