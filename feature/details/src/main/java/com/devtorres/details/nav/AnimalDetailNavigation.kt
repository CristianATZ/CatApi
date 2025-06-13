package com.devtorres.details.nav

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devtorres.details.AnimalDetailScreen
import com.devtorres.details.AnimalDetailsViewModel
import com.devtorres.navigation.Screen

const val ANIMAL_ARGS_ID = "animalId"

internal class AnimalDetailArgs(val animalId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                checkNotNull(
                    savedStateHandle[ANIMAL_ARGS_ID]
                ){ "El argumento $ANIMAL_ARGS_ID no puede ser nulo"} as String
            )
    }

fun NavGraphBuilder.animalDetailNavigation(
    onNavigateUp: () -> Unit
) {
    composable(
        route = Screen.Detail.route,
        arguments = listOf(
            navArgument(ANIMAL_ARGS_ID) {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            fadeIn(animationSpec = tween(1000))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(1000))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(1000))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(1000))
        }
    ) { backStackEntry ->
        // inicializar viewmodel
        val animalDetailViewModel : AnimalDetailsViewModel = hiltViewModel(backStackEntry)
        AnimalDetailScreen(
            animalDetailViewModel = animalDetailViewModel,
            onNavigateUp = onNavigateUp
        )
    }
}