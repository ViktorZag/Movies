package com.filmcenter.movies.presentation.auth.model

import com.filmcenter.movies.R

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorState: AuthError? = null,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false
)

enum class AuthError(val message: Int) {
    InvalidEmail(R.string.invalid_email_msg),
    InvalidPassword(R.string.invalid_password_msg),
    WrongPassword(R.string.wrong_password_msg),
    UserNotExist(R.string.user_not_exist_msg),
    UserAlreadyExist(R.string.user_already_exist_msg),
    InternetConnectionErr(R.string.internet_connection_err_msg),
    UnknownError(R.string.unknown_err_msg)
}