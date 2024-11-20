package com.example.githubrepos.features.repos_list.presentation.viewmodel

sealed interface ReposActions {

    data class SearchRepos(val reposName: String): ReposActions
    data class UpdateSearchQuery(val query: String): ReposActions
}