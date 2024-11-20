package com.example.githubrepos.features.register.signup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.common.domain.repository.IFacebookAuth
import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.features.register.signup.domain.repository.ISignupRepository
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: ISignupRepository,
    private val facebookAuth: IFacebookAuth
) : ViewModel() {

    private val _signupState: MutableStateFlow<SignupState> = MutableStateFlow(SignupState())
    val signupState = _signupState.asStateFlow()

    private val eventChannel = Channel<SignupEvent>(Channel.UNLIMITED)
      val singleEvent: Flow<SignupEvent> = eventChannel.receiveAsFlow()

    private val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _signupState.update { it.copy(user = currentUser) }
        }
    }

    fun onAction(actions: SignupActions) {
        when (actions) {
            is SignupActions.Signup -> {
                signUp(actions.email, actions.password, actions.name)
            }

            is SignupActions.LoginWithFacebook -> {
                registerFacebookCallback(actions.callbackManager, actions.loginManager)
            }
        }
    }

    private fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _signupState.update { it.copy(isLoading = true) }
            when (val result = repository.signup(email, password, name)) {
                is Resource.Failure -> {
                    _signupState.update {
                        it.copy(
                            error = result.exception.message,
                            isLoading = false
                        )
                    }
                }

                Resource.Loading -> {
                    _signupState.update { it.copy(isLoading = true, error = null) }
                }

                is Resource.Success -> {
                    _signupState.update {
                        it.copy(
                            user = result.result,
                            isLoading = false,
                            error = null
                        )
                    }
                    eventChannel.trySend(SignupEvent.SignupSuccessfully("Signup Successfully"))

                }
            }
        }
    }

    private fun loginWithFacebook(accessToken: AccessToken) {
        _signupState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = facebookAuth.handleFacebookAccessToken(accessToken)) {
                is Resource.Failure -> {
                    _signupState.update {
                        it.copy(
                            error = result.exception.message,
                            isLoading = false
                        )
                    }
                }

                Resource.Loading -> {
                    _signupState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _signupState.update {
                        it.copy(
                            user = result.result,
                            error = "Login Successfully with facebook",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun registerFacebookCallback(
        callbackManager: CallbackManager,
        loginManager: LoginManager
    ) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                result?.let {
                    loginWithFacebook(it.accessToken)
                }
            }

            override fun onCancel() {
                _signupState.update { it.copy(error = "Facebook login is cancelled") }
            }

            override fun onError(error: FacebookException?) {
                _signupState.update { it.copy(error = "Facebook login error: ${error?.message}") }

            }
        })
    }
}












