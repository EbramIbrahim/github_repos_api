package com.example.githubrepos.features.repos_list.data.repository.remote

import com.example.githubrepos.common.utils.Constant.GITHUB_BARER_TOKEN
import com.example.githubrepos.common.utils.Constant.ITEMS_PER_PAGE
import com.example.githubrepos.features.repos_list.data.model.GithubUserDtoItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ReposApi {

    @GET("/users/{username}/repos")
    suspend fun getAllRepos(
        @Header("Authorization") auth: String = GITHUB_BARER_TOKEN,
        @Path("username") reposName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = ITEMS_PER_PAGE,
    ): List<GithubUserDtoItem>


}