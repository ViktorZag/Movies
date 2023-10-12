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
import com.filmcenter.movies.presentation.auth.model.AuthError
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
                Log.d("tag", "${signUpResult.signInError}")
                when (signUpResult.signInError) {
                    AuthErrors.ERROR_NETWORK -> {
                        uiState =
                            uiState.copy(
                                errorState = AuthError.InternetConnectionErr,
                                isLoading = false
                            )
                    }
                    AuthErrors.ERROR_USER_ALREADY_EXIST -> {
                        uiState =
                            uiState.copy(
                                errorState = AuthError.UserAlreadyExist,
                                isLoading = false
                            )
                    }
                    else -> {
                        uiState =
                            uiState.copy(errorState = AuthError.UnknownError, isLoading = false)
                    }
                }
            }
        }
    }

    private fun validateInputs(
        email: String,
        password: String,
        confirmPassword: String
    ): AuthError? {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,19}$"
        return when {
            !email.matches(emailRegex.toRegex()) -> AuthError.InvalidEmail
            !password.matches(passwordRegex.toRegex()) -> AuthError.InvalidPassword
            confirmPassword != password -> AuthError.PasswordsDoesntMatch
            else -> null
        }
    }
}