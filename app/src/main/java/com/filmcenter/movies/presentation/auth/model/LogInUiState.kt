package com.filmcenter.movies.presentation.auth.model

import com.filmcenter.movies.data.auth.AuthErrors

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorState: AuthErrors? = null,
    val isLoading: Boolean = false,
    val isOneTapSignInDeclined: Boolean = false,
    val isLoginSuccessful: Boolean = false
)