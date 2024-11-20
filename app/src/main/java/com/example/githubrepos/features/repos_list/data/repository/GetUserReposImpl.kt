package com.example.githubrepos.features.repos_list.data.repository

import com.example.githubrepos.features.repos_list.data.model.GithubUserDtoItem
import com.example.githubrepos.features.repos_list.data.repository.remote.ReposApi
import com.example.githubrepos.features.repos_list.domain.repository.IGetUserRepos
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GetUserReposImpl @Inject constructor(
    private val reposApi: ReposApi,
    private val auth: FirebaseAuth
): IGetUserRepos {

    override suspend fun getUserRepos(reposName: String, currentPage: Int): List<GithubUserDtoItem> {
        return reposApi.getAllRepos(reposName = reposName, page = currentPage)
    }

    override fun logout() {
        auth.signOut()
    }
}








