package com.devtorres.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devtorres.common.AsyncImageLoader
import com.devtorres.model.Animal

@Composable
fun AnimalCard(
    animal: Animal,
    onNavigateToAnimalDetail: (String) -> Unit
) {
    Card(
        onClick = {
            onNavigateToAnimalDetail(animal.id)
        },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImageLoader(
            url = animal.imageUri,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
        )

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = animal.breedName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Badge(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Text(
                    text = animal.breedOrigin,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}