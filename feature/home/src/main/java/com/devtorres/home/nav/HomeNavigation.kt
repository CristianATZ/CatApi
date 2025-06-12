package com.devtorres.home.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devtorres.home.HomeScreen
import com.devtorres.navigation.Screen

fun NavGraphBuilder.homeNavigation(
    onNavigateToAnimalDetail: (String) -> Unit
) {
    composable(
        route = Screen.Home.route
    ) {
        HomeScreen(
            onNavigateToAnimalDetail = onNavigateToAnimalDetail
        )
    }
}