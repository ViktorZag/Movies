package com.filmcenter.movies.presentation.auth

object Constants {
    const val EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}+\$"
    const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,19}$"
}