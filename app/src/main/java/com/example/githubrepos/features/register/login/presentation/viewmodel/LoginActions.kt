package com.example.githubrepos.features.register.login.presentation.viewmodel


sealed interface LoginActions {
    data class Login(val email: String, val password: String): LoginActions
}