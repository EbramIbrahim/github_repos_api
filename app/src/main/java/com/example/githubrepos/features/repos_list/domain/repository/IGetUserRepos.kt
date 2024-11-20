package com.example.githubrepos.features.repos_list.domain.repository

import com.example.githubrepos.features.repos_list.data.model.GithubUserDtoItem

interface IGetUserRepos {

    suspend fun getUserRepos(reposName: String, currentPage: Int): List<GithubUserDtoItem>
    fun logout()


}