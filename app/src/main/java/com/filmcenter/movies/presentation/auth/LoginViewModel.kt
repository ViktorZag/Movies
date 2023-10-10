package com.filmcenter.movies.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filmcenter.movies.presentation.auth.model.AuthError
import com.filmcenter.movies.presentation.auth.model.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun updateUiState(email: String, password: String) {
        uiState = uiState.copy(
            email = email,
            password = password
        )
    }

    fun logIn() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(4000)
            uiState = uiState.copy(isLoading = false)
        }
    }

    private fun validateInputs(email: String, password: String): AuthError? {
        return when {
            email.trim().isEmpty() || !email.trim().contains("@") -> AuthError.InvalidEmail
            password.length < 8 || password.contains(" ") -> AuthError.InvalidPassword
            else -> null
        }
    }

}