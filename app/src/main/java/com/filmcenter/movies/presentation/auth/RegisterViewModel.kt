package com.filmcenter.movies.presentation.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filmcenter.movies.data.auth.AuthErrors
import com.filmcenter.movies.data.auth.AuthResult
import com.filmcenter.movies.data.auth.AuthRepository
import com.filmcenter.movies.presentation.auth.Constants.EMAIL_REGEX
import com.filmcenter.movies.presentation.auth.Constants.PASSWORD_REGEX
import com.filmcenter.movies.presentation.auth.model.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun updateUiState(email: String, password: String, confirmPassword: String) {
        uiState = uiState.copy(
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )
    }

    fun registerUser() {
        viewModelScope.launch {
            uiState = uiState.copy(
                errorState = validateInputs(
                    uiState.email,
                    uiState.password,
                    uiState.confirmPassword
                )
            )
            if (validateInputs(
                    uiState.email,
                    uiState.password,
                    uiState.confirmPassword
                ) != null
            ) return@launch
            uiState = uiState.copy(isLoading = true)
            val signUpResult =
                authRepository.signUpUserWithEmailAndPassword(uiState.email, uiState.password)
            if (signUpResult is AuthResult.Success) {
                uiState = uiState.copy(isRegistrationSuccessful = true)
            } else if (signUpResult is AuthResult.Failure) {
                uiState = uiState.copy(errorState = signUpResult.error, isLoading = false)
            }
        }
    }

    private fun validateInputs(
        email: String,
        password: String,
        confirmPassword: String
    ): AuthErrors? {
        return when {
            !email.matches(EMAIL_REGEX.toRegex()) -> AuthErrors.INVALID_EMAIL
            !password.matches(PASSWORD_REGEX.toRegex()) -> AuthErrors.INVALID_PASSWORD
            confirmPassword != password -> AuthErrors.PASSWORDS_DONT_MATCH
            else -> null
        }
    }
}