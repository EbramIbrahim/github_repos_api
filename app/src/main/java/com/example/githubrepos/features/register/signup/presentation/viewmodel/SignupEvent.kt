package com.example.githubrepos.features.register.signup.presentation.viewmodel

sealed interface SignupEvent {
    data object Idle: SignupEvent
    data class SignupSuccessfully(val message: String): SignupEvent
}