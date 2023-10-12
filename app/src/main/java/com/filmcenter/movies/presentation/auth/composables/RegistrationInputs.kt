package com.filmcenter.movies.presentation.auth.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.filmcenter.movies.R
import com.filmcenter.movies.presentation.auth.model.RegisterUiState
import com.filmcenter.movies.presentation.theme.MoviesTheme

@Composable
fun RegistrationInputs(
    modifier: Modifier,
    registrUiState: RegisterUiState,
    onUserInputChange: (String, String, String) -> Unit,
    onSubmit: () -> Unit,
) {
    // Login Inputs Section
    Column(modifier = modifier.fillMaxWidth()) {

        // Email
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MoviesTheme.dimens.paddingNormal),
            value = registrUiState.email,
            onValueChange = { email ->
                onUserInputChange(email, registrUiState.password, registrUiState.confirmPassword)
            },
            label = stringResource(id = R.string.login_email),
            enabled = !registrUiState.isLoading,
            error = registrUiState.errorState
        )
        // Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MoviesTheme.dimens.paddingSmall),
            value = registrUiState.password,
            onValueChange = { password ->
                onUserInputChange(registrUiState.email, password, registrUiState.confirmPassword)
            },
            label = stringResource(id = R.string.login_password_label),
            enabled = !registrUiState.isLoading,
            error = registrUiState.errorState,
            imeAction = ImeAction.Next
        )

        // Confirm Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MoviesTheme.dimens.paddingSmall),
            value = registrUiState.confirmPassword,
            onValueChange = { confirmPassword ->
                onUserInputChange(registrUiState.email, registrUiState.password, confirmPassword)
            },
            label = stringResource(id = R.string.registration_confirm_password_label),
            enabled = !registrUiState.isLoading,
            error = registrUiState.errorState
        )

        // Registration Submit Button
        NormalButton(
            modifier = Modifier.padding(top = MoviesTheme.dimens.paddingExtraLarge),
            text = stringResource(id = R.string.registration_button_text),
            enabled = !registrUiState.isLoading,
            onClick = onSubmit
        )

    }
}