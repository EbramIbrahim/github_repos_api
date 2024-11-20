package com.example.githubrepos.common.data.repository

import android.util.Log
import com.example.githubrepos.common.domain.repository.IFacebookAuth
import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.common.utils.await
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FacebookAuthImpl @Inject constructor(
    private val auth: FirebaseAuth
) : IFacebookAuth {

    override suspend fun handleFacebookAccessToken(token: AccessToken): Resource<FirebaseUser> {
        return try {
            val credential = FacebookAuthProvider.getCredential(token.token)
            val authResult = withContext(Dispatchers.IO) {
                auth.signInWithCredential(credential).await()
            }
            Log.e("FacebookLogin", "User: ${authResult.user}")
            return Resource.Success(authResult.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}