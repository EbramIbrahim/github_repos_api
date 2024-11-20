package com.example.githubrepos.features.register.signup.presentation.viewmodel

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.LoginManager

sealed interface SignupActions {
    data class Signup(val email: String, val password: String, val name: String) : SignupActions
    data class LoginWithFacebook(
        val callbackManager: CallbackManager,
        val loginManager: LoginManager
    ) : SignupActions
}