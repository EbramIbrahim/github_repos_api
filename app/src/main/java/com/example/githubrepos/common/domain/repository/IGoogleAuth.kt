package com.example.githubrepos.common.domain.repository

import com.example.githubrepos.common.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface IGoogleAuth {
    val currentUser: FirebaseUser?
    suspend fun signInWithGoogle(): Resource<FirebaseUser>
//    suspend fun signInWithFaceBook(loginManager: LoginManager)
}