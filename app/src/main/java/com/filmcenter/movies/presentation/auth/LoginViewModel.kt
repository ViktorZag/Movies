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
    var googleLoginIntentSender by mutableStateOf<IntentSender?>(null)

    fun updateUiState(email: String, password: String) {
        uiState = uiState.copy(email, password, null)
    }

    fun onGoogleLoginClick() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.oneTapSignInWithGoogle()
            when (result) {
                is AuthResult.Success -> {
                    googleLoginIntentSender = result.data
                    uiState = uiState.copy(isLoading = false)
                }
                is AuthResult.Failure ->
                    uiState = uiState.copy(
                        isOneTapSignInDeclined = result.error == AuthErrors.ONE_TAP_SIGNIN_DECLINED_ERROR,
                        errorState = result.error,
                        isLoading = false
                    )
            }
        }
    }

    fun logInWithIntent(intent: Intent) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.firebaseSignInWithGoogle(intent)
            if (result is AuthResult.Success && result.data != null) {
                uiState = uiState.copy(isLoginSuccessful = true)
            } else if (result is AuthResult.Failure) {
                uiState = uiState.copy(errorState = result.error, isLoading = false)
                Log.d("tag", "Exception ${result.error}")
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
                uiState =
                    uiState.copy(errorState = result.error, isLoading = false)
            }
        }
    }

    private fun validateInputs(email: String, password: String): AuthErrors? {
        return when {
            !email.matches(Constants.EMAIL_REGEX.toRegex()) -> AuthErrors.INVALID_EMAIL
            !password.matches(Constants.PASSWORD_REGEX.toRegex()) -> AuthErrors.INVALID_PASSWORD
            else -> null
        }
    }

}