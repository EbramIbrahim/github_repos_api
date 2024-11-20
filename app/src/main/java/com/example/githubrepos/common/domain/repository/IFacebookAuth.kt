package com.example.githubrepos.common.domain.repository

import com.example.githubrepos.common.utils.Resource
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser

interface IFacebookAuth {
    suspend fun handleFacebookAccessToken(token: AccessToken): Resource<FirebaseUser>
}