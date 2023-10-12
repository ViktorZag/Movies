package com.filmcenter.movies.data.auth

sealed class AuthResult<out T> {

    data class Success<out T>(
        val data: T?
    ) : AuthResult<T>()

    data class Failure(
        val signInError: AuthErrors
    ) : AuthResult<Nothing>()
}

enum class AuthErrors {
    ERROR_USER_NOT_FOUND,
    ERROR_WRONG_CREDENTIALS,
    ERROR_USER_ALREADY_EXIST,
    ERROR_NETWORK,
    UNKNOWN_ERROR
}
