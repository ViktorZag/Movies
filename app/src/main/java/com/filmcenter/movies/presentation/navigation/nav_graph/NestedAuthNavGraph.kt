package com.filmcenter.movies.presentation.navigation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.filmcenter.movies.presentation.auth.LoginScreen
import com.filmcenter.movies.presentation.auth.RegistrationScreen

fun NavGraphBuilder.authGraph(navController: NavController, route: String) {
    navigation(
        route = route,
        startDestination = "login"
    ) {
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
                onNavigateToRegistration = { navController.navigate("registration") },
                onNavigateToForgotPassword = { /*TODO*/ },
                onNavigateToAuthenticatedRoute = {
                    navController.popBackStack("login", inclusive = true)
                    navController.navigate("empty")
                },
                onNavigateToMain = { navController.navigate("main") })
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
            RegistrationScreen(onNavigateBack = { navController.popBackStack() },
                onNavigateToAuthenticatedRoute = { navController.popBackStack() })
        }
    }
}