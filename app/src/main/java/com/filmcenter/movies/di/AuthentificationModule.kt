package com.filmcenter.movies.di

import android.app.Application
import android.content.Context
import com.filmcenter.movies.R
import com.filmcenter.movies.data.auth.AuthRepository
import com.filmcenter.movies.data.auth.AuthRepositoryImpl
import com.filmcenter.movies.data.auth.ProfileRepository
import com.filmcenter.movies.data.auth.ProfileRepositoryImpl
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class AuthentificationModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ): SignInClient = Identity.getSignInClient(context)

    @Provides
    fun provideSignInRequest(
        app: Application
    ): BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ): GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ): GoogleSignInClient = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        signInRequest: BeginSignInRequest
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest
    )

    @Provides
    fun provideProfileRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        signInClient: GoogleSignInClient
    ): ProfileRepository = ProfileRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInClient = signInClient
    )
}