package com.example.githubrepos.features.register.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.features.register.login.domain.repository.ILoginRepository
import com.example.githubrepos.features.register.signup.presentation.viewmodel.SignupEvent
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
class LoginViewModel @Inject constructor(
    private val repository: ILoginRepository
) : ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val eventChannel = Channel<LoginEvent>(Channel.UNLIMITED)
    val singleEvent: Flow<LoginEvent> = eventChannel.receiveAsFlow()

    fun onAction(actions: LoginActions) {
        when (actions) {
            is LoginActions.Login -> {
                login(actions.email, actions.password)
            }

        }
    }

    private val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _loginState.update { it.copy(user = currentUser) }
        }
        isLoggedIn()
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true) }
            when (val result = repository.login(email, password)) {
                is Resource.Failure -> {
                    _loginState.update {
                        it.copy(
                            error = result.exception.message.toString(),
                            isLoading = false,
                            isLoggedIn = false,
                        )
                    }
                }

                Resource.Loading -> {
                    _loginState.update {
                        it.copy(
                            isLoading = true,
                            error = null,
                            isLoggedIn = false,
                        )
                    }
                }

                is Resource.Success -> {
                    _loginState.update {
                        it.copy(
                            user = result.result,
                            isLoading = false,
                            error = null
                        )
                    }
                    eventChannel.trySend(LoginEvent.LoginSuccessfully("Signup Successfully"))
                }
            }
        }
    }

    fun isLoggedIn() {
        val isLoggedIn = repository.isLoggedIn()
        _loginState.update { it.copy(isLoggedIn = isLoggedIn) }
    }

}











