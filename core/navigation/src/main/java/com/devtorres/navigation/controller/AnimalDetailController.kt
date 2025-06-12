package com.devtorres.navigation.controller

import androidx.navigation.NavController
import com.devtorres.navigation.Screen

fun NavController.navigateToAnimalDetail(animalId: String) {
    this.navigate(Screen.Detail.createRoute(animalId)) {
        launchSingleTop = true
    }
}