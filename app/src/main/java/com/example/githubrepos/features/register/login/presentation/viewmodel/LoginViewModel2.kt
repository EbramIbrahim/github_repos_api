package com.example.githubrepos.features.register.login.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.githubrepos.common.presentation.viewmodel.GithubReposViewModel
import com.example.githubrepos.common.presentation.viewmodel.ViewAction
import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.features.register.login.domain.repository.ILoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel2 @Inject constructor(
    private val repository: ILoginRepository
): GithubReposViewModel<LoginContracts.LoginState, LoginContracts.LoginEvent, LoginContracts.LoginActions>(
    LoginContracts.LoginState.initial()) {


    init {
        if (repository.isLoggedIn()) {
            onTriggerAction(LoginContracts.LoginActions.IsLoggedIn)
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            setState(oldViewState.copy(isLoading = true))
            when(val result = repository.login(email, password)) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                Resource.Loading -> setState(oldViewState.copy(isLoading = true, exception = null))
                is Resource.Success -> sendEvent(LoginContracts.LoginEvent.LoginSuccessfully("Login Successfully"))
            }
            setState(oldViewState.copy(isLoading = false))
        }
    }



    override fun clearState() {
        setState(LoginContracts.LoginState.initial())
    }

    override fun onTriggerAction(action: ViewAction?) {
        setState(oldViewState.copy(action = action, exception = null))
        when(action) {
            is LoginContracts.LoginActions.Login -> {
                login(action.email, action.password)
            }
            is LoginContracts.LoginActions.IsLoggedIn -> {
                sendEvent(LoginContracts.LoginEvent.LoginSuccessfully("already logged in"))
            }
        }
    }
}