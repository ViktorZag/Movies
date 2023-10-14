package com.filmcenter.movies.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filmcenter.movies.R
import com.filmcenter.movies.data.auth.AuthErrors
import com.filmcenter.movies.presentation.auth.composables.ProgressIndicator
import com.filmcenter.movies.presentation.auth.composables.RegistrationInputs
import com.filmcenter.movies.presentation.auth.composables.TitleText
import com.filmcenter.movies.presentation.theme.MoviesTheme
import kotlinx.coroutines.delay

@Composable
fun RegistrationScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit
) {

    val uiState = viewModel.uiState

    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main card Content for Registration
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MoviesTheme.dimens.paddingLarge)
                    .padding(top = MoviesTheme.dimens.paddingExtraLarge)
            ) {

                Column(
                    modifier = Modifier
                        .padding(horizontal = MoviesTheme.dimens.paddingLarge)
                        .padding(bottom = MoviesTheme.dimens.paddingExtraLarge)
                ) {
                    TitleText(
                        modifier = Modifier.padding(top = MoviesTheme.dimens.paddingLarge),
                        text = stringResource(id = R.string.registration_heading_text)
                    )
                    RegistrationInputs(
                        modifier = Modifier.padding(top = MoviesTheme.dimens.paddingLarge),
                        registrUiState = uiState,
                        onUserInputChange = viewModel::updateUiState,
                        onSubmit = {
                            viewModel.registerUser()
                        }
                    )
                }
            }
            Row(
                modifier = Modifier.padding(MoviesTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Don't have an account?
                Text(text = stringResource(id = R.string.already_registered),
                    color = MaterialTheme.colorScheme.onSurface)

                //Register
                Text(
                    modifier = Modifier
                        .padding(start = MoviesTheme.dimens.paddingExtraSmall)
                        .clickable {
                            onNavigateBack.invoke()
                        },
                    text = stringResource(id = R.string.log_in),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        val snackbarHostState = remember { SnackbarHostState() }
        SnackbarHost(
            modifier = Modifier.align(Alignment.TopCenter),
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    content = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when {
                                    uiState.isRegistrationSuccessful -> "Registration Successful"
                                    uiState.errorState != null -> stringResource(id = uiState.errorState.message)
                                    else -> ""
                                }
                            )
                        }
                    }
                )
            }
        )
        LaunchedEffect(uiState.errorState) {
            if (
                uiState.errorState == AuthErrors.ERROR_WRONG_CREDENTIALS ||
                uiState.errorState == AuthErrors.ERROR_USER_ALREADY_EXIST ||
                uiState.errorState == AuthErrors.NETWORK_ERROR ||
                uiState.errorState == AuthErrors.UNKNOWN_ERROR
            ) {
                snackbarHostState.showSnackbar(
                    "Error message",
                    duration = SnackbarDuration.Short
                )
            }
        }
        LaunchedEffect(uiState.isRegistrationSuccessful) {
            if (uiState.isRegistrationSuccessful) {
                snackbarHostState.showSnackbar(
                    "Registered successfuly",
                    duration = SnackbarDuration.Short
                )
                delay(300)
                onNavigateToAuthenticatedRoute.invoke()
            }
        }
        ProgressIndicator(isShown = uiState.isLoading)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    MoviesTheme {
        RegistrationScreen(onNavigateBack = {}, onNavigateToAuthenticatedRoute = {})
    }
}