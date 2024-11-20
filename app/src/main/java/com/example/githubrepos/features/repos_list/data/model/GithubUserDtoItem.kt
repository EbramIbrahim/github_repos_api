package com.example.githubrepos.features.repos_list.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserDtoItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
)