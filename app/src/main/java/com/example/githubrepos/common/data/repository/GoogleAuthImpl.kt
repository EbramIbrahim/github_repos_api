package com.example.githubrepos.common.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.githubrepos.common.domain.repository.IGoogleAuth
import com.example.githubrepos.common.utils.Constant.GOOGLE_SERVER_CLIENT_ID
import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.common.utils.await
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class GoogleAuthImpl(
    private val context: Context,
) : IGoogleAuth {

    private val credentialManager = CredentialManager.create(context)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signInWithGoogle(): Resource<FirebaseUser> {
        return try {
            val result = buildCredentialRequest()
            return handleSignIn(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }


    private suspend fun handleSignIn(result: GetCredentialResponse): Resource<FirebaseUser> {
        val credential = result.credential

        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken, null
                )

                val authResult = withContext(Dispatchers.IO) {
                    auth.signInWithCredential(authCredential).await()
                }
                return Resource.Success(authResult.user!!)

            } catch (e: GoogleIdTokenParsingException) {
                e.printStackTrace()
                return Resource.Failure(e)
            }
        } else {
            return Resource.Failure(Exception())
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        GOOGLE_SERVER_CLIENT_ID
                    )
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()
        return credentialManager.getCredential(
            request = request, context = context
        )
    }

}















