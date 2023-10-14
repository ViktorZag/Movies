package com.filmcenter.movies.presentation.auth.model

import com.filmcenter.movies.data.auth.AuthErrors

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorState: AuthErrors? = null,
    val isLoading: Boolean = false,
    val isRegistrationSuccessful: Boolean = false
)

