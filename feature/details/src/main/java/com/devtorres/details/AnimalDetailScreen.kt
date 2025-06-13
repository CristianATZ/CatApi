package com.devtorres.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devtorres.common.AsyncImageLoader
import com.devtorres.details.components.AnimalCharacteristic
import com.devtorres.model.AnimalInfo
import com.devtorres.model.BreedInfo

@Composable
fun AnimalDetailScreen(
    onNavigateUp: () -> Unit,
    animalDetailViewModel: AnimalDetailsViewModel
) {
    val uiState by animalDetailViewModel.uiState.collectAsStateWithLifecycle()
    val animalInfo by animalDetailViewModel.animalInfo.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if(uiState == DetailsUiState.Idle && animalInfo != null) {
            AnimalInformation(
                animalInfo = animalInfo!!,
                onNavigateUp = onNavigateUp
            )
        } else if(uiState == DetailsUiState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_fetching_animal_info),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onNavigateUp
                ) {
                    Text(
                        text = stringResource(R.string.btn_regresar)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimalInformation(
    animalInfo: AnimalInfo,
    onNavigateUp: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImageLoader(
            url = animalInfo.url,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(16.dp).navigationBarsPadding()
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = animalInfo.breeds.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = onNavigateUp,
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                }

                Spacer(Modifier.size(8.dp))

                Row(
                    modifier = Modifier.alpha(0.5f)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )

                    Spacer(Modifier.size(8.dp))

                    Text(
                        text = animalInfo.breeds.getOriginString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(Modifier.size(8.dp))

                Row(
                    modifier = Modifier.alpha(0.5f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timelapse,
                        contentDescription = null
                    )

                    Spacer(Modifier.size(8.dp))

                    Text(
                        text = animalInfo.breeds.getLifeSpanString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(Modifier.size(24.dp))

                Text(
                    text = animalInfo.breeds.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.alpha(0.5f)
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.animal_temperament),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(Modifier.size(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    animalInfo.breeds.getTemperamentList().forEach { temp ->
                        Badge(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ) {
                            Text(
                                text = temp,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.animal_characteristics),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                AnimalCharacteristic(
                    label = R.string.affection,
                    progress = animalInfo.breeds.affection_level.toFloat() / BreedInfo.MAX_VALUE,
                    counter = animalInfo.breeds.getAffectionLevelString()
                )

                AnimalCharacteristic(
                    label = R.string.adaptability,
                    progress = animalInfo.breeds.adaptability.toFloat() / BreedInfo.MAX_VALUE,
                    counter = animalInfo.breeds.getAdaptabilityString()
                )

                AnimalCharacteristic(
                    label = R.string.childFriendly,
                    progress = animalInfo.breeds.child_friendly.toFloat() / BreedInfo.MAX_VALUE,
                    counter = animalInfo.breeds.getChildFriendlyString()
                )

                AnimalCharacteristic(
                    label = R.string.intelligence,
                    progress = animalInfo.breeds.intelligence.toFloat() / BreedInfo.MAX_VALUE,
                    counter = animalInfo.breeds.getIntelligenceString()
                )

                AnimalCharacteristic(
                    label = R.string.energy,
                    progress = animalInfo.breeds.energy_level.toFloat() / BreedInfo.MAX_VALUE,
                    counter = animalInfo.breeds.getEnergyLevelString()
                )

                AnimalCharacteristic(
                    label = R.string.grooming,
                    progress = animalInfo.breeds.grooming.toFloat() / BreedInfo.MAX_VALUE,
                    counter = animalInfo.breeds.getGroomingString()
                )

                AnimalCharacteristic(
                    label = R.string.rare,
                    progress = animalInfo.breeds.rare.toFloat() / BreedInfo.MAX_VALUE,
                    counter = animalInfo.breeds.getRareString()
                )
            }
        }
    }
}