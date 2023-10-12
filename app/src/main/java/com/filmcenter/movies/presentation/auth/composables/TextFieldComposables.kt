package com.filmcenter.movies.presentation.auth.composables

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.filmcenter.movies.presentation.auth.model.AuthError

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: AuthError? = null,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        trailingIcon = {
            IconButton(onClick = {
                isPasswordVisible = !isPasswordVisible
            }) {
                val passwordVisibilityIcon =
                    if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                Icon(
                    imageVector = passwordVisibilityIcon,
                    contentDescription = null
                )
            }
        },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        enabled = enabled,
        isError = error != null,
        supportingText = {
            if (error == AuthError.InvalidPassword) {
                ErrorTextInputField(text = stringResource(id = error.message))
            }
        }
    )
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: AuthError? = null,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        enabled = enabled,
        isError = error != null,
        supportingText = {
            if (error == AuthError.InvalidEmail) {
                ErrorTextInputField(text = stringResource(id = error.message))
            }
        }
    )

}