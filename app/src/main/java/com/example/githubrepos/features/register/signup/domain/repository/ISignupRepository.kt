package com.example.githubrepos.features.register.signup.domain.repository

import com.example.githubrepos.common.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface ISignupRepository {
    val currentUser: FirebaseUser?
    suspend fun signup(email: String, password: String, name: String): Resource<FirebaseUser>
}