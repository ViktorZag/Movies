package com.filmcenter.movies.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.filmcenter.movies.R
import com.filmcenter.movies.presentation.auth.composables.DividerTextComponent
import com.filmcenter.movies.presentation.auth.composables.RegistrationInputs
import com.filmcenter.movies.presentation.auth.composables.SignInButton
import com.filmcenter.movies.presentation.auth.composables.TitleText
import com.filmcenter.movies.presentation.theme.AppTheme
import com.filmcenter.movies.presentation.theme.MoviesTheme

@Composable
fun RegistrationScreen(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit
) {

    val registrationState = remember {
        registerViewModel.uiState
    }

    if (registrationState.isRegistrationSuccessful) {
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    } else {
        // Full Screen Content
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
                    .padding(AppTheme.dimens.paddingLarge)
                    .padding(top = AppTheme.dimens.paddingExtraLarge)
            ) {

                Column(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.paddingLarge)
                        .padding(bottom = AppTheme.dimens.paddingExtraLarge)
                ) {

                    // Heading Registration
                    TitleText(
                        modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge),
                        text = stringResource(id = R.string.registration_heading_text)
                    )

                    /**
                     * Registration Inputs Composable
                     */
                    RegistrationInputs(
                        registrUiState = registrationState,
                        onUserInputChange = registerViewModel::updateUiState,
                        onSubmit = {

                        }
                    )
                }
            }
            Row(
                modifier = Modifier.padding(AppTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Don't have an account?
                Text(text = stringResource(id = R.string.already_registered))

                //Register
                Text(
                    modifier = Modifier
                        .padding(start = AppTheme.dimens.paddingExtraSmall)
                        .clickable {
                            //onNavigateToRegistration.invoke()
                        },
                    text = stringResource(id = R.string.log_in),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            DividerTextComponent(modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingLarge))
            Spacer(modifier = Modifier.height(AppTheme.dimens.paddingSmall))
            SignInButton(
                modifier = Modifier
                    .height(AppTheme.dimens.normalButtonSize)
                    .width(AppTheme.dimens.normalButtonSize),
                icon = painterResource(id = R.drawable.ic_google_logo),
                onClick = {})
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    MoviesTheme {
        RegistrationScreen(onNavigateBack = {}, onNavigateToAuthenticatedRoute = {})
    }
}