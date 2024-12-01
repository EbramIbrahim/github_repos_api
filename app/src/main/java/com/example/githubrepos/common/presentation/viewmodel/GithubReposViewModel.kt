package com.example.githubrepos.common.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class GithubReposViewModel<State : ViewState, Event : ViewEvent, Action : ViewAction>(
    initialState: State
) : IGithubReposViewModel<State, Event, Action>, ViewModel() {


    private val _viewState: MutableStateFlow<State> = MutableStateFlow(initialState)
    override val viewState: StateFlow<State>
        get() = _viewState

    val oldViewState: State
        get() = viewState.value

    private val _singleEventChannel = Channel<Event>(capacity = Channel.UNLIMITED)
    override val singleEvent: Flow<Event>
        get() = _singleEventChannel.receiveAsFlow()


    private val _actionFlow = MutableSharedFlow<Action>(extraBufferCapacity = Int.MAX_VALUE)
    override fun processIntent(action: Action) {
        check(_actionFlow.tryEmit(action)) { "Failed to emit Actiom: $action" }
    }


    protected fun sendEvent(event: Event) {
        println("Current Event: $event")
        _singleEventChannel.trySend(event)
    }

    fun setState(newState: State) {
        _viewState.value = newState
        println("Current state: ${viewState.value}")
    }
    abstract fun clearState()


    private val _actionSharedFlow: SharedFlow<Action>
        get() = _actionFlow

    abstract fun onTriggerAction(action: ViewAction?)

    init {
        viewModelScope.launch {
            _actionSharedFlow.collect {
                onTriggerAction(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _singleEventChannel.close()
    }


}

















