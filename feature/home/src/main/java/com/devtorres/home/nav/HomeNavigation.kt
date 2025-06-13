package com.devtorres.home.nav

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
        route = Screen.Home.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(250)
            ) + fadeOut(animationSpec = tween(250))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(250)
            ) + fadeOut(animationSpec = tween(250))
        }
    ) {
        val homeViewModel : HomeViewModel = hiltViewModel()
        HomeScreen(
            homeViewModel = homeViewModel,
            onNavigateToAnimalDetail = onNavigateToAnimalDetail
        )
    }
}