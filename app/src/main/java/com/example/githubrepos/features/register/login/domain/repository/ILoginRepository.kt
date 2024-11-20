package com.example.githubrepos.features.register.login.domain.repository

import com.example.githubrepos.common.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface ILoginRepository {
    fun logout()
    fun isLoggedIn(): Boolean
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
}