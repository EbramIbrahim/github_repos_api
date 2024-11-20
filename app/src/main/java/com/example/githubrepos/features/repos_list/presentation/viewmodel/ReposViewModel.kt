package com.example.githubrepos.features.repos_list.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.features.repos_list.data.model.GithubUserDtoItem
import com.example.githubrepos.features.repos_list.domain.repository.IGetUserRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReposViewModel @Inject constructor(
    private val repository: IGetUserRepos
) : ViewModel() {

    private val _reposState = MutableStateFlow(ReposState())
    val reposState = _reposState.asStateFlow()

    private val _searchQuery = MutableStateFlow("EbramIbrahim")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun logout() {
        repository.logout()
    }

    private val _repos = mutableStateListOf<GithubUserDtoItem>()
    val repos: List<GithubUserDtoItem> get() = _repos

    private var currentPage = 1
    var isLastPage = false
    var isLoading = false

    fun loadRepos(username: String) {
        if (isLoading || isLastPage) return

        isLoading = true
        viewModelScope.launch {
            try {
                val newRepos = repository.getUserRepos(username, currentPage)

                if (newRepos.isEmpty()) {
                    isLastPage = true
                } else {
                    _repos.addAll(newRepos)
                    currentPage++
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _reposState.update { it.copy(error = e.message) }
            } finally {
                isLoading = false
            }
        }
    }

    fun resetPagination() {
        _repos.clear()
        currentPage = 1
        isLastPage = false

    }
}
















