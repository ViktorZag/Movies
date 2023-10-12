package com.filmcenter.movies.data.auth

import com.google.firebase.auth.FirebaseUser

interface ProfileRepository {
    val user: FirebaseUser?

    suspend fun signOut(): AuthResult<Boolean>

    suspend fun revokeAccess(): AuthResult<Boolean>
}
