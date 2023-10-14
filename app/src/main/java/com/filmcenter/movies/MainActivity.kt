package com.filmcenter.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filmcenter.movies.presentation.MoviesApp
import com.filmcenter.movies.presentation.auth.LoginScreen
import com.filmcenter.movies.presentation.auth.RegistrationScreen
import com.filmcenter.movies.presentation.navigation.nav_graph.authGraph
import com.filmcenter.movies.presentation.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesApp()
        }
    }
}