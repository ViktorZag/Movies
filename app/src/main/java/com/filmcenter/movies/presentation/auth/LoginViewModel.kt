package com.filmcenter.movies.presentation.auth

import android.content.Intent
import android.content.IntentSender
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
import com.filmcenter.movies.presentation.auth.model.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState(isLoginSuccessful = false /*authRepository.isUserAuthenticated*/))
        private set

    fun updateUiState(email: String, password: String) {
        uiState = uiState.copy(email, password, null)
    }

    suspend fun logInWithGoogle(): IntentSender? {
        return authRepository.oneTapSignInWithGoogle()
    }

    fun logInWithIntent(intent: Intent) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.firebaseSignInWithGoogle(intent)
            if (result is AuthResult.Success && result.data != null) {
                uiState = uiState.copy(isLoginSuccessful = true)
            } else if (result is AuthResult.Failure) {

                Log.d("tag", "Exception ${result.signInError}")
            }
        }
    }

    fun logInWithEmailAndPassword() {
        viewModelScope.launch {
            uiState = uiState.copy(errorState = validateInputs(uiState.email, uiState.password))
            if (validateInputs(uiState.email, uiState.password) != null) return@launch
            uiState = uiState.copy(isLoading = true)
            val result =
                authRepository.logInUserWithEmailAndPassword(uiState.email, uiState.password)
            if (result is AuthResult.Success) {
                uiState = uiState.copy(isLoginSuccessful = true, isLoading = false)
            } else if (result is AuthResult.Failure) {
                Log.d("tag", "${result.signInError}")
                when (result.signInError) {
                    AuthErrors.ERROR_USER_NOT_FOUND -> {
                        uiState =
                            uiState.copy(errorState = AuthError.WrongCredentials, isLoading = false)
                    }

                    AuthErrors.ERROR_NETWORK -> {
                        uiState =
                            uiState.copy(
                                errorState = AuthError.InternetConnectionErr,
                                isLoading = false
                            )
                    }

                    AuthErrors.ERROR_WRONG_CREDENTIALS -> {
                        uiState =
                            uiState.copy(
                                errorState = AuthError.WrongCredentials,
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

    private fun validateInputs(email: String, password: String): AuthError? {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,19}$"
        return when {
            !email.matches(emailRegex.toRegex()) -> AuthError.InvalidEmail
            !password.matches(passwordRegex.toRegex()) -> AuthError.InvalidPassword
            else -> null
        }
    }

}