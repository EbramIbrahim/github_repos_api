package com.example.githubrepos.features.register.login.presentation.viewmodel

import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val user: FirebaseUser? = null,
    val isLoggedIn: Boolean = false,
    val email: String? = null,
    val password: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)
