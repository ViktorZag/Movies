package com.filmcenter.movies.presentation.auth

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filmcenter.movies.R
import com.filmcenter.movies.presentation.auth.composables.DividerTextComponent
import com.filmcenter.movies.presentation.auth.composables.LoginInputs
import com.filmcenter.movies.presentation.auth.composables.SignInButton
import com.filmcenter.movies.presentation.auth.composables.TitleText
import com.filmcenter.movies.presentation.auth.model.AuthError
import com.filmcenter.movies.presentation.theme.MoviesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegistration: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit
) {

    val uiState = viewModel.uiState

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("tag", "result ok")
                    viewModel.logInWithIntent(
                        result.data ?: return@rememberLauncherForActivityResult
                    )
                }
            })

    if (uiState.isLoginSuccessful) {
        LaunchedEffect(Unit) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
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

                // Main card Content for Login
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MoviesTheme.dimens.paddingLarge)
                        .padding(top = MoviesTheme.dimens.paddingLarge)
                        .padding(bottom = MoviesTheme.dimens.paddingNormal)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = MoviesTheme.dimens.paddingLarge)
                            .padding(bottom = MoviesTheme.dimens.paddingExtraLarge)
                            .padding(top = MoviesTheme.dimens.paddingLarge)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.movies_logo),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(132.dp)
                                .padding(top = MoviesTheme.dimens.paddingSmall),
                            contentDescription = stringResource(id = R.string.login_heading_text)
                        )
                        Spacer(modifier = Modifier.height(MoviesTheme.dimens.paddingSmall))
                        // Heading Login
                        TitleText(
                            modifier = Modifier.padding(top = MoviesTheme.dimens.paddingSmall),
                            text = stringResource(id = R.string.login_heading_text)
                        )
                        LoginInputs(
                            modifier = Modifier.padding(top = MoviesTheme.dimens.paddingNormal),
                            loginState = uiState,
                            onUserInputChange = viewModel::updateUiState,
                            onSubmit = {
                                viewModel.logInWithEmailAndPassword()
                            },
                            onForgotPasswordClick = onNavigateToForgotPassword
                        )

                    }
                }
                // Register Section
                Row(
                    modifier = Modifier.padding(MoviesTheme.dimens.paddingSmall),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Don't have an account?
                    Text(
                        text = stringResource(id = R.string.do_not_have_account),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = MoviesTheme.dimens.paddingTooSmall)
                            .clickable {
                                onNavigateToRegistration.invoke()
                            },
                        text = stringResource(id = R.string.register),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                DividerTextComponent(modifier = Modifier.padding(horizontal = MoviesTheme.dimens.paddingLarge))
                Spacer(modifier = Modifier.height(MoviesTheme.dimens.paddingExtraSmall))
                val coroutineScope = rememberCoroutineScope()
                SignInButton(
                    modifier = Modifier
                        .size(MoviesTheme.dimens.minButtonSize),
                    icon = painterResource(id = R.drawable.ic_google_logo),
                    onClick = {
                        coroutineScope.launch {
                            val signInIntentSender = viewModel.logInWithGoogle()
                            Log.d("tag", "signInIntentSender $signInIntentSender")
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    })
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
                                    text = if (uiState.errorState == null) "" else stringResource(id = uiState.errorState.message)
                                )
                            }
                        }
                    )
                }
            )
            LaunchedEffect(uiState.errorState) {
                if (
                    uiState.errorState == AuthError.WrongCredentials ||
                    uiState.errorState == AuthError.InternetConnectionErr ||
                    uiState.errorState == AuthError.UnknownError
                ) {
                    snackbarHostState.showSnackbar(
                        "Error message",
                        duration = SnackbarDuration.Short
                    )
                }
            }
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.06f)
                        .background(MaterialTheme.colorScheme.onSurface)
                )
                CircularProgressIndicator()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MoviesTheme {
        LoginScreen(
            onNavigateToForgotPassword = {},
            onNavigateToRegistration = {},
            onNavigateToAuthenticatedRoute = {}
        )
    }
}