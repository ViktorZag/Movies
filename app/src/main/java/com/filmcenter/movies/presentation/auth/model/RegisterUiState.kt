package com.filmcenter.movies.presentation.auth.model

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorState: AuthError? = null,
    val isLoading: Boolean = false,
    val isRegistrationSuccessful: Boolean = false
)

