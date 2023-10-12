package com.filmcenter.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filmcenter.movies.presentation.auth.LoginScreen
import com.filmcenter.movies.presentation.auth.RegistrationScreen
import com.filmcenter.movies.presentation.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                val navHostController = rememberNavController()
                NavHost(navController = navHostController, startDestination = "login") {
                    composable("login",
                        enterTransition = {
                            when (initialState.destination.route) {
                                "login" -> null
                                else ->
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(500)
                                    )
                            }
                        },
                        exitTransition = {
                            when (initialState.destination.route) {
                                "login" ->
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(500)
                                    )
                                else -> null
                            }
                        },
                        popEnterTransition = {
                            when (initialState.destination.route) {
                                "registration" ->
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )

                                else -> null
                            }
                        },
                        popExitTransition = {
                            when (targetState.destination.route) {
                                "registration" ->
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )

                                else -> null
                            }
                        }) {
                        LoginScreen(
                            onNavigateToRegistration = { navHostController.navigate("registration") },
                            onNavigateToForgotPassword = { /*TODO*/ },
                            onNavigateToAuthenticatedRoute = {
                                navHostController.popBackStack("login", inclusive = true)
                                navHostController.navigate("empty")
                            })
                    }
                    composable("registration",
                        enterTransition = {
                            when (initialState.destination.route) {
                                "registration" -> null
                                else ->
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(500)
                                    )
                            }
                        },
                        exitTransition = {
                            when (initialState.destination.route) {
                                "registration" ->
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )
                                else -> null
                            }
                        },
                        popEnterTransition = {
                            when (initialState.destination.route) {
                                "login" ->
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )

                                else -> null
                            }
                        },
                        popExitTransition = {
                            when (targetState.destination.route) {
                                "login" ->
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )

                                else -> null
                            }
                        }) {
                        RegistrationScreen(onNavigateBack = { navHostController.popBackStack() },
                            onNavigateToAuthenticatedRoute = { navHostController.popBackStack() })
                    }
                    composable("empty") {

                    }
                }
            }
        }
    }
}