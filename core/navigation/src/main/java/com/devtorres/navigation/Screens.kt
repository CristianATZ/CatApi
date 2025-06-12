package com.devtorres.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")

    data object Detail : Screen("detail/{animalId}") {
        private const val BASE_DETAIL_ROUTE = "detail"
        fun createRoute(animalId: String) = "$BASE_DETAIL_ROUTE/$animalId"
    }
}