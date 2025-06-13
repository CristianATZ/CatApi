package com.devtorres.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devtorres.designsystem.components.CatApiTopAppBar
import com.devtorres.home.components.AnimalCard
import com.devtorres.model.Animal

@Composable
fun HomeScreen(
    onNavigateToAnimalDetail: (String) -> Unit,
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val animalList by homeViewModel.animalList.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CatApiTopAppBar()

        if(uiState == HomeUiState.Idle || uiState == HomeUiState.Loading) {
            AnimalList(
                animalList = animalList,
                uiState = uiState,
                onNavigateToAnimalDetail = onNavigateToAnimalDetail,
                onFetchNextAnimalPage = homeViewModel::fetchNextAnimalPage
            )
        } else {
            Text(
                text = stringResource(R.string.error_fetching_animals),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = homeViewModel::fetchNextAnimalPage
            ) {
                Text(
                    text = stringResource(R.string.btn_retry)
                )
            }
        }
    }
}

@Composable
fun AnimalList(
    animalList: List<Animal>,
    uiState: HomeUiState,
    onNavigateToAnimalDetail: (String) -> Unit,
    onFetchNextAnimalPage: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(
                items = animalList,
                key = { _, animal -> animal.id }
            ) { index, animal ->
                /*if((index+lumbralCarga) >= animalList.size && uiState != HomeUiState.Loading) {
                    onFetchNextAnimalPage()
                }*/

                AnimalCard(
                    animal = animal,
                    onNavigateToAnimalDetail = onNavigateToAnimalDetail
                )
            }

            item {
                Button(
                    onClick = onFetchNextAnimalPage,
                    enabled = uiState != HomeUiState.Loading,
                ) {
                    if(uiState == HomeUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.btn_load_more)
                        )
                    }
                }
            }
        }
    }
}