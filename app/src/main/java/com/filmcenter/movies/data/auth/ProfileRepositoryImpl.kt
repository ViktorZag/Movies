package com.filmcenter.movies.data.auth

import com.filmcenter.movies.presentation.gallery.model.User
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private var signInClient: GoogleSignInClient
) : ProfileRepository {
    override val user: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signOut(): AuthResult<Boolean> {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            AuthResult.Success(true)
        } catch (e: Exception) {
            AuthResult.Failure(AuthErrors.UNKNOWN_ERROR)
        }
    }

    override suspend fun revokeAccess(): AuthResult<Boolean> {
        return try {
            auth.currentUser?.apply {
                // db.collection(USERS).document(uid).delete().await()
                signInClient.revokeAccess().await()
                oneTapClient.signOut().await()
                auth.signOut()
                delete().await()
            }
            AuthResult.Success(true)
        } catch (e: Exception) {
            AuthResult.Failure(AuthErrors.UNKNOWN_ERROR)
        }
    }
}

fun FirebaseUser.toUser(): User {
    return User(
        name = this.displayName ?: "",
        email = this.email ?: "",
        photoUrl = if (this.photoUrl != null) this.photoUrl.toString() else ""
    )
}
