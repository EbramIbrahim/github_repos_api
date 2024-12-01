package com.example.githubrepos.features.register.signup.presentation.viewmodel

import com.example.githubrepos.common.presentation.viewmodel.ViewAction
import com.example.githubrepos.common.presentation.viewmodel.ViewEvent
import com.example.githubrepos.common.presentation.viewmodel.ViewState
import com.facebook.CallbackManager
import com.facebook.login.LoginManager

interface SignupContract {
    sealed interface SignupActions : ViewAction {

        data class Signup(val email: String, val password: String, val name: String) : SignupActions

        data class LoginWithFacebook(
            val callbackManager: CallbackManager,
            val loginManager: LoginManager
        ) : SignupActions
    }

    sealed interface SignupEvent : ViewEvent {
        data class SignupSuccessfully(val message: String) : SignupEvent
    }


    data class SignupState(
        val isLoading: Boolean, val exception: Exception?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = SignupState(
                isLoading = false, exception = null, action = null
            )
        }
    }
}