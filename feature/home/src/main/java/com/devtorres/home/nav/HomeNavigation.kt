package com.devtorres.home.nav

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devtorres.home.HomeScreen
import com.devtorres.home.HomeViewModel
import com.devtorres.navigation.Screen

fun NavGraphBuilder.homeNavigation(
    onNavigateToAnimalDetail: (String) -> Unit
) {
    composable(
        route = Screen.Home.route
    ) {
        val homeViewModel : HomeViewModel = hiltViewModel()
        HomeScreen(
            homeViewModel = homeViewModel,
            onNavigateToAnimalDetail = onNavigateToAnimalDetail
        )
    }
}