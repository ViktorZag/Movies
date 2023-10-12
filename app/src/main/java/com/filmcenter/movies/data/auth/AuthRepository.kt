package com.filmcenter.movies.data.auth

import android.content.Intent
import android.content.IntentSender
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val isUserAuthenticated: Boolean

    suspend fun oneTapSignInWithGoogle(): IntentSender?

    suspend fun firebaseSignInWithGoogle(intent: Intent): AuthResult<FirebaseUser>
    suspend fun signUpUserWithEmailAndPassword(email:String,password:String) : AuthResult<FirebaseUser>
    suspend fun logInUserWithEmailAndPassword(email:String, password:String) : AuthResult<FirebaseUser>
}