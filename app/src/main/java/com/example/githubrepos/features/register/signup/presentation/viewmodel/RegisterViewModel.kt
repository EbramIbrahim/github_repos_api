package com.example.githubrepos.features.register.signup.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.githubrepos.common.domain.repository.IFacebookAuth
import com.example.githubrepos.common.presentation.viewmodel.GithubReposViewModel
import com.example.githubrepos.common.presentation.viewmodel.ViewAction
import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.features.register.signup.domain.repository.ISignupRepository
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: ISignupRepository,
    private val facebookAuth: IFacebookAuth
) : GithubReposViewModel<SignupContract.SignupState, SignupContract.SignupEvent, SignupContract.SignupActions>(
    SignupContract.SignupState.initial()
) {


    private fun signup(email: String, password: String, name: String) {
        viewModelScope.launch {
            setState(oldViewState.copy(isLoading = true))
            when (val result = repository.signup(email, password, name)) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                Resource.Loading -> setState(oldViewState.copy(isLoading = true, exception = null))
                is Resource.Success -> sendEvent(SignupContract.SignupEvent.SignupSuccessfully("Sign up successfully"))
            }
            setState(oldViewState.copy(isLoading = false))
        }
    }

    private fun loginWithFacebook(accessToken: AccessToken) {
        viewModelScope.launch {
            when (val result = facebookAuth.handleFacebookAccessToken(accessToken)) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                Resource.Loading -> setState(oldViewState.copy(isLoading = true, exception = null))
                is Resource.Success -> sendEvent(SignupContract.SignupEvent.SignupSuccessfully("Login Successfully with Facebook"))
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
                setState(oldViewState.copy(exception = CancellationException()))
            }

            override fun onError(error: FacebookException?) {
                setState(oldViewState.copy(exception = error))
            }
        })
    }


    override fun clearState() {
        setState(SignupContract.SignupState.initial())
    }

    override fun onTriggerAction(action: ViewAction?) {
        setState(oldViewState.copy(action = action, exception = null))
        when (action) {
            is SignupContract.SignupActions.Signup -> {
                signup(action.email, action.password, action.name)
            }

            is SignupContract.SignupActions.LoginWithFacebook -> {
                registerFacebookCallback(action.callbackManager, action.loginManager)
            }
        }
    }
}