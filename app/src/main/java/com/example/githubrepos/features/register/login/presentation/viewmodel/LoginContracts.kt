package com.example.githubrepos.features.register.login.presentation.viewmodel

import com.example.githubrepos.common.presentation.viewmodel.ViewAction
import com.example.githubrepos.common.presentation.viewmodel.ViewEvent
import com.example.githubrepos.common.presentation.viewmodel.ViewState

interface LoginContracts {

    sealed interface LoginActions: ViewAction {
        data class Login(val email: String, val password: String): LoginActions
        data object IsLoggedIn: LoginActions
    }

    sealed interface LoginEvent: ViewEvent {
        data class LoginSuccessfully(val message: String): LoginEvent
    }

    data class LoginState(
        val isLoading: Boolean, val exception: Exception?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LoginState(
                isLoading = false, exception = null, action = null
            )
        }
    }

}