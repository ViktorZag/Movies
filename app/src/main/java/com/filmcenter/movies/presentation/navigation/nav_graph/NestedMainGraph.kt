package com.filmcenter.movies.presentation.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.filmcenter.movies.presentation.gallery.DemoScreen

fun NavGraphBuilder.mainGraph(navigationController: NavHostController, route: String) {
    navigation(
        route = route,
        startDestination = "home_screen"
    ) {
        composable("home_screen") {
            DemoScreen()
        }
    }
}