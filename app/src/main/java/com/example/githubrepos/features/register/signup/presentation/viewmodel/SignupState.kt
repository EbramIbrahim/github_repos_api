package com.example.githubrepos.features.register.signup.presentation.viewmodel

import com.google.firebase.auth.FirebaseUser

data class SignupState(
    val user: FirebaseUser? = null,
    val email: String? = null,
    val password: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)
