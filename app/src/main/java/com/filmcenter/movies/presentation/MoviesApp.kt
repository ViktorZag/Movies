package com.filmcenter.movies.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.filmcenter.movies.presentation.navigation.nav_graph.authGraph
import com.filmcenter.movies.presentation.navigation.nav_graph.mainGraph
import com.filmcenter.movies.presentation.theme.MoviesTheme

@Composable
fun MoviesApp(darkTheme: Boolean = true) {

    MoviesTheme {
        val navHostController = rememberNavController()
        NavHost(navController = navHostController, startDestination = "auth") {

            authGraph(navHostController, route = "auth")

            mainGraph(navHostController, route = "main")
        }
    }
}
