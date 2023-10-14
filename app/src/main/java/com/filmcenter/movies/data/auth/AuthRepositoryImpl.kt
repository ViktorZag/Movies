package com.filmcenter.movies.data.auth

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.filmcenter.movies.data.auth.AuthResult.Success
import com.filmcenter.movies.data.auth.AuthResult.Failure
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "AuthRepository"

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private var signInRequest: BeginSignInRequest
) : AuthRepository {

    override val isUserAuthenticated: Boolean = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle(): AuthResult<IntentSender?> {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Success(signInResult.pendingIntent.intentSender)
        } catch (e: ApiException) {
            Log.e(TAG, "oneTapSignInWithGoogle", e)
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> Failure(AuthErrors.ONE_TAP_SIGNIN_DECLINED_ERROR)
                CommonStatusCodes.NETWORK_ERROR -> Failure(AuthErrors.NETWORK_ERROR)
                else -> Failure(AuthErrors.UNKNOWN_ERROR)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception on oneTapSignInWithGoogle", e)
            if (e is CancellationException) throw e
            Failure(AuthErrors.UNKNOWN_ERROR)
        }
    }

    override suspend fun firebaseSignInWithGoogle(intent: Intent): AuthResult<FirebaseUser> {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user!!
            Success(user)
        } catch (e: FirebaseAuthInvalidUserException) {
            Log.e(TAG, "SignInWithCredential error.", e)
            Failure(AuthErrors.ERROR_WRONG_CREDENTIALS)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e(TAG, "SignInWithCredential error.", e)
            Failure(AuthErrors.ERROR_WRONG_CREDENTIALS)
        } catch (e: Exception) {
            Log.e(TAG, "Something bad happened.", e)
            if (e is CancellationException) throw e
            Failure(AuthErrors.UNKNOWN_ERROR)
        }
    }

    override suspend fun signUpUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult<FirebaseUser> {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user!!
            oneTapClient.signOut().await()
            auth.signOut()
            AuthResult.Success(user)
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.e(TAG, "CreateUserWithEmailAndPassword error.", e)
            AuthResult.Failure(AuthErrors.ERROR_USER_ALREADY_EXIST)
        } catch (e: Exception) {
            Log.e(TAG, "Something bad happened.", e)
            if (e is CancellationException) throw e
            AuthResult.Failure(AuthErrors.UNKNOWN_ERROR)
        }
    }

    override suspend fun logInUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult<FirebaseUser> {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(user)
        } catch (e: FirebaseNetworkException) {
            Log.e(TAG, "Network exception.", e)
            Failure(AuthErrors.NETWORK_ERROR)
        } catch (e: FirebaseException) {
            Log.e(TAG, "Wrong credentials.", e)
            Failure(AuthErrors.ERROR_WRONG_CREDENTIALS)
        } catch (e: Exception) {
            Log.e(TAG, "Something bad happened.", e)
            if (e is CancellationException) throw e
            Failure(AuthErrors.UNKNOWN_ERROR)
        }
    }
}