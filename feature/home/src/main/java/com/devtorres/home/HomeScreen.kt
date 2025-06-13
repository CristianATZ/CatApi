package com.devtorres.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onNavigateToAnimalDetail: (String) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val animalList by homeViewModel.animalList.collectAsStateWithLifecycle()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    homeViewModel = homeViewModel,
                    onSaveFilter = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CatApiTopAppBar(
                onOpenFilterDrawer = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            )

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
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DrawerContent(
    onSaveFilter: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val breeds = remember {
        listOf(
            "Abyssinian" to "abys",
            "Aegean" to "aege",
            "American Bobtail" to "abob",
            "Americal Curl" to "curl",
            "Arabian Mau" to "amau",
            "Balinese" to "bali"
        )
    }

    val category = remember {
        listOf(
            "Cajas" to "5",
            "Ropa" to "15",
            "Sombreros" to "1",
            "Lavamanos" to "14",
            "Espacio" to "2",
            "Lentes" to "4",
            "Corbatas" to "7",
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.filter_warning),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Justify,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(8.dp)
        )

        HorizontalDivider()

        Column {
            Text(
                text = stringResource(R.string.breeds_filter),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.size(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                breeds.forEach {  breed ->
                    FilterChip(
                        selected = false,

                        onClick = {

                        },
                        label = {
                            Text(
                                text = breed.first,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            }
        }

        Column {
            Text(
                text = stringResource(R.string.category_filter),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.size(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                category.forEach {  breed ->
                    FilterChip(
                        selected = false,

                        onClick = {

                        },
                        label = {
                            Text(
                                text = breed.first,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
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