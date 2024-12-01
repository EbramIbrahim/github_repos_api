package com.example.githubrepos.common.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IGithubReposViewModel<State: ViewState, Event: ViewEvent, Action: ViewAction> {

    val singleEvent: Flow<Event>

    val viewState: StateFlow<State>


    fun processIntent(action: Action)


}