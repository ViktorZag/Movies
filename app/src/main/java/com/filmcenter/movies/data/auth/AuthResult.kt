package com.filmcenter.movies.data.auth

import com.filmcenter.movies.R

sealed class AuthResult<out T> {

    data class Success<out T>(
        val data: T?
    ) : AuthResult<T>()

    data class Failure(
        val error: AuthErrors
    ) : AuthResult<Nothing>()
}

enum class AuthErrors(val message: Int) {
    INVALID_EMAIL(R.string.invalid_email_msg),
    INVALID_PASSWORD(R.string.invalid_password_msg),
    PASSWORDS_DONT_MATCH(R.string.passwords_not_match_msg),
    ERROR_WRONG_CREDENTIALS(R.string.user_not_exist_msg),
    ERROR_USER_ALREADY_EXIST(R.string.user_already_exist_msg),
    NETWORK_ERROR(R.string.internet_connection_err_msg),
    ONE_TAP_SIGNIN_DECLINED_ERROR(R.string.one_tap_signin_err_msg),
    UNKNOWN_ERROR(R.string.unknown_err_msg)
}
