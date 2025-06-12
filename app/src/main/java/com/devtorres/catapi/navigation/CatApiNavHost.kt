package com.devtorres.catapi.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devtorres.details.nav.animalDetailNavigation
import com.devtorres.home.nav.homeNavigation
import com.devtorres.navigation.Screen
import com.devtorres.navigation.controller.navigateToAnimalDetail

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CatApiNavhost(
    navController: NavHostController = rememberNavController(),
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            homeNavigation(
                onNavigateToAnimalDetail = { animalId ->
                    navController.navigateToAnimalDetail(animalId = animalId)
                }
            )

            animalDetailNavigation(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}