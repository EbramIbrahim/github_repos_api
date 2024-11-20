package com.example.githubrepos.features.register.login.presentation.viewmodel

sealed interface LoginEvent {
    data object Idle: LoginEvent
    data class LoginSuccessfully(val message: String): LoginEvent
}