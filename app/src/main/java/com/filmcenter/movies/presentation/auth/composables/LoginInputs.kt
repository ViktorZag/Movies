package com.filmcenter.movies.presentation.auth.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.filmcenter.movies.R
import com.filmcenter.movies.presentation.auth.model.LoginUiState
import com.filmcenter.movies.presentation.theme.MoviesTheme

@Composable
fun LoginInputs(
    modifier: Modifier = Modifier,
    loginState: LoginUiState,
    onUserInputChange: (String, String) -> Unit,
    onSubmit: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {

    // Login Inputs Section
    Column(modifier = modifier.fillMaxWidth()) {

        // Email
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = loginState.email,
            onValueChange = { email ->
                onUserInputChange(email, loginState.password)
            },
            label = stringResource(id = R.string.login_email),
            enabled = !loginState.isLoading,
            error = loginState.errorState
        )


        // Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MoviesTheme.dimens.paddingSmall),
            value = loginState.password,
            onValueChange = { password ->
                onUserInputChange(loginState.email, password)
            },
            label = stringResource(id = R.string.login_password_label),
            enabled = !loginState.isLoading,
            error = loginState.errorState
        )

        // Forgot Password
        Text(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .clickable {
                    onForgotPasswordClick.invoke()
                },
            text = stringResource(id = R.string.forgot_password),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium
        )

        // Login Submit Button
        NormalButton(
            modifier = Modifier.padding(top = MoviesTheme.dimens.paddingNormal),
            text = stringResource(id = R.string.login_button_text),
            enabled = !loginState.isLoading,
            onClick = onSubmit
        )

    }
}